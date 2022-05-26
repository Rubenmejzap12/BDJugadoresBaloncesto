package es.rubenmejias.jugadoresbaloncesto;


import es.rubenmejias.jugadoresbaloncesto.entities.Equipo;
import es.rubenmejias.jugadoresbaloncesto.entities.Jugador;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.persistence.Query;
import javax.persistence.RollbackException;

public class SecondaryController implements Initializable{

    private Jugador jugador;
    private boolean nuevoJugador;
    
    private static final char BASE = 'B';
    private static final char ALERO = 'A';
    private static final char PIVOT = 'P';
    
    private static final String CARPETA_FOTOS = "Fotos";

    @FXML
    private BorderPane rootSecondary;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldApellido;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private DatePicker datePickerFecha;
    @FXML
    private TextField textFieldIdJugador;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldSalario;
    @FXML
    private TextField textFieldHijos;
    @FXML
    private RadioButton radioButtonBase;
    @FXML
    private ToggleGroup PosicionGroup;
    @FXML
    private RadioButton radioButtonAlero;
    @FXML
    private RadioButton radioButtonPivot;
    @FXML
    private CheckBox checkBoxActivo;
    @FXML
    private ImageView imageViewFoto;
    @FXML
    private ComboBox<Equipo> comboBoxEquipo;

    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb){  
    }
    
    public void setJugador(Jugador jugador, boolean nuevoJugador) {
        App.em.getTransaction().begin();
        if (!nuevoJugador){
            this.jugador = App.em.find(Jugador.class, jugador.getIdJugad());
        }else{
            this.jugador = jugador;
        }
        this.nuevoJugador = nuevoJugador;
        mostrarDatos();
    }
    
    
    private void mostrarDatos(){
        textFieldNombre.setText(jugador.getNombre());
        textFieldApellido.setText(jugador.getApellidos());
        textFieldEmail.setText(jugador.getEmail());
       
        
        if(jugador.getTelefono() != null){
            textFieldTelefono.setText(String.valueOf(jugador.getTelefono()));
        }
        
        if(jugador.getNumHijos() != null){
            textFieldHijos.setText(String.valueOf(jugador.getNumHijos()));
        }
        
        if(jugador.getSalario() != null){
            textFieldSalario.setText(String.valueOf(jugador.getSalario()));
        }
        
        
        if(jugador.getActivo() != null){
            checkBoxActivo.setSelected(jugador.getActivo());
        }
        
        if(jugador.getPosicion() != null){
            switch (jugador.getPosicion()){
                case BASE:
                    radioButtonBase.setSelected(true);
                    break;
                case ALERO:
                    radioButtonAlero.setSelected(true);
                    break;   
                case PIVOT:
                    radioButtonPivot.setSelected(true);
                    break;    
            }
        }
        
        if(jugador.getFechaNacimiento() != null){
            Date date = jugador.getFechaNacimiento();
            Instant instant = date.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate localDate = zdt.toLocalDate();
            datePickerFecha.setValue(localDate);
        }
        
        Query queryEquipoFindAll = App.em.createNamedQuery("Equipo.findAll");
        List<Equipo> listEquipo = queryEquipoFindAll.getResultList();
        
        comboBoxEquipo.setItems(FXCollections.observableList(listEquipo));
        if(jugador.getEquipo() != null){
            comboBoxEquipo.setValue(jugador.getEquipo());
        }
        comboBoxEquipo.setCellFactory((ListView<Equipo> l) -> new ListCell<Equipo>(){
            @Override
            protected void updateItem (Equipo equipo, boolean empty){
                super.updateItem(equipo, empty);
                if(equipo == null || empty){
                    setText("");
                }else {
                    setText(equipo.getCodigo() + "-" + equipo.getNombre());
                } 
            }
        });
        
        //Formato para el valor mostrado actualmente como seleccionado
        comboBoxEquipo.setConverter(new StringConverter<Equipo>(){
            @Override
            public String toString (Equipo equipo){
                if(equipo == null ){
                    return null;
                }else {
                    return equipo.getCodigo() + "-" + equipo.getNombre();
                } 
            }

            @Override
            public Equipo fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        if(jugador.getFoto() != null){
            String imageFileName = jugador.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if (file.exists()){
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encuentra la imágen");
                alert.showAndWait();
            }
        }
    }
    
    
    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        boolean errorFormato = false;
        
        jugador.setNombre(textFieldNombre.getText());
        jugador.setApellidos(textFieldApellido.getText());
        jugador.setEmail(textFieldEmail.getText());
        jugador.setTelefono(textFieldTelefono.getText());
        
        
        if(!textFieldHijos.getText().isEmpty()){
            try{
                jugador.setNumHijos(Short.valueOf(textFieldHijos.getText()));
            }catch(NumberFormatException ex){
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Número de hijos no válido");
                alert.showAndWait();
                textFieldHijos.requestFocus();
            }
        }
        
        if(!textFieldSalario.getText().isEmpty()){
            try{
                jugador.setSalario(BigDecimal.valueOf(Double.valueOf(textFieldSalario.getText()).doubleValue()));
            }catch(NumberFormatException ex){
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Salario no valido");
                alert.showAndWait();
                textFieldSalario.requestFocus();
            }
        }
        
        jugador.setActivo(checkBoxActivo.isSelected());
        
        if (radioButtonBase.isSelected()){
            jugador.setPosicion(BASE);
        }
        if (radioButtonAlero.isSelected()){
            jugador.setPosicion(ALERO);
        }
        if (radioButtonPivot.isSelected()){
            jugador.setPosicion(PIVOT);
        }
        
        if(datePickerFecha.getValue() != null){
            LocalDate localDate = datePickerFecha.getValue();
            ZonedDateTime zoneDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zoneDateTime.toInstant();
            Date date = Date.from(instant);
            jugador.setFechaNacimiento(date);
        }else {
            jugador.setFechaNacimiento(null);
        }
        
        jugador.setEquipo(comboBoxEquipo.getValue());
        
        if (!errorFormato){
            try{
                if(jugador.getIdJugad() == null){
                    System.out.println("Guardando nuevo jugador en BD");
                    App.em.persist(jugador);
                }else{
                    System.out.println("Actualizando jugador en BD");
                    App.em.merge(jugador);
                }
                App.em.getTransaction().commit();
                
                App.setRoot("primary");
                
            }catch(RollbackException ex){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No se ha podido guardar los cambios. "
                    + "Compruebe que los datos cumplen los requisitos");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            } catch (IOException ex) {
                //No se ha podido cargar la ventana primary
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    @FXML
    private void onActionButtonCancelar(ActionEvent event){
        App.em.getTransaction().rollback();
        
        try{
            App.setRoot("primary");
        }catch (IOException ex){
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onActionButtonExaminar(ActionEvent event) {
        File carpertaFotos = new File(CARPETA_FOTOS);
        if(!carpertaFotos.exists()){
            carpertaFotos.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes (jpg, png)", "*.png"),
                 new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        
        File file = fileChooser.showOpenDialog(rootSecondary.getScene().getWindow());
        if (file != null){
            try{
                Files.copy(file.toPath(), new File(CARPETA_FOTOS + "/" + file.getName()).toPath());
                jugador.setFoto(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } catch (FileAlreadyExistsException ex){
                Alert alert = new Alert(Alert.AlertType.WARNING,"Nombre de archivo duplicado");
                alert.showAndWait();
            }catch (IOException ex){
                Alert alert = new Alert(Alert.AlertType.WARNING,"No se ha podido guardar la imagen");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar supresion ed imagen");
        alert.setHeaderText("¿Desea SUPRIMIR el archivo asociado a la imagen, \n"
                + "quitar la foto pero MANTENER el archivo, \no CANCELAR la operación?");
        alert.setContentText("Elija a opción deseada");
        
        ButtonType buttonTypeEiminar = new ButtonType("Suprimir");
        ButtonType buttonTypeMantener = new ButtonType("Mantener");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(buttonTypeEiminar, buttonTypeMantener, buttonTypeCancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == buttonTypeEiminar) {
            String imageFileName = jugador.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if(file.exists()){
                file.delete();
            }
            jugador.setFoto(null);
            imageViewFoto.setImage(null);
        } else if (result.get() == buttonTypeMantener){
            jugador.setFoto(null);
            imageViewFoto.setImage(null);
        }
    }
}
