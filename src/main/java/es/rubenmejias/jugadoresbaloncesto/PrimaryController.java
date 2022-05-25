package es.rubenmejias.jugadoresbaloncesto;


import es.rubenmejias.jugadoresbaloncesto.entities.Jugador;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.Query;

public class PrimaryController implements Initializable{
    
    private Jugador jugadorSeleccionado;
    @FXML
    private TableView<Jugador> tableViewJugadores;
    @FXML
    private TableColumn<Jugador, Short> columnIdJugador;
    @FXML
    private TableColumn<Jugador, String> columnNombre;
    @FXML
    private TableColumn<Jugador, String> columnApellidos;
    @FXML
    private TableColumn<Jugador, String> columnEmail;
    @FXML
    private TableColumn<Jugador, String> columnEquipo;
    @FXML
    private Button buttonNuevo;
    @FXML
    private Button buttonEditar;
    @FXML
    private Button buttonSuprimir;
    @FXML
    private TextArea textAreaNombre;
    @FXML
    private TextArea textAreaApellidos;
    @FXML
    private Button buttonGuardar;
    @FXML
    private Button buttonBuscar;
    @FXML
    private CheckBox checkBoxCoincide;
    @FXML
    private TextField textFieldBuscar;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        columnIdJugador.setCellValueFactory(new PropertyValueFactory<>("idJugad"));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnEquipo.setCellValueFactory(
                cellData -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if (cellData.getValue().getEquipo() != null){
                        String nombreEquip = cellData.getValue().getEquipo().getNombre();
                        property.setValue(nombreEquip);
                        
                    }
                    return property;
                }
        );
        tableViewJugadores.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                jugadorSeleccionado = newValue;
                if(jugadorSeleccionado != null){
                    textAreaNombre.setText(jugadorSeleccionado.getNombre());
                    textAreaApellidos.setText(jugadorSeleccionado.getApellidos());
                }else {
                    textAreaNombre.setText("");
                    textAreaApellidos.setText("");
                }
        });
                
        cargarTodosJugadores();
    }
    
    private void cargarTodosJugadores(){
        Query queryJugadorFindAll = App.em.createNamedQuery("Jugador.findAll");
        List<Jugador> listJugador = queryJugadorFindAll.getResultList();
        tableViewJugadores.setItems(FXCollections.observableArrayList(listJugador));
    }

    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
        if (jugadorSeleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar");
            alert.setHeaderText("¿Deseas suprimir el siguiente registro?");
            alert.setContentText(jugadorSeleccionado.getNombre() + ""
                + jugadorSeleccionado.getApellidos());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                App.em.getTransaction().begin();
                App.em.remove(jugadorSeleccionado);
                App.em.getTransaction().commit();
                tableViewJugadores.getItems().remove(jugadorSeleccionado);
                tableViewJugadores.getFocusModel().focus(null);
                tableViewJugadores.requestFocus();
            }else {
                int numFilaSeleccionada = tableViewJugadores.getSelectionModel().getSelectedIndex();
                tableViewJugadores.getItems().set(numFilaSeleccionada, jugadorSeleccionado);
                TablePosition pos = new TablePosition(tableViewJugadores, numFilaSeleccionada, null);
                tableViewJugadores.getFocusModel().focus(pos);
                tableViewJugadores.requestFocus();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }
        
    }
    
    @FXML
    private void onActionButtonNuevo(ActionEvent event) {
        try{
           App.setRoot("secondary");
           SecondaryController secondaryController = (SecondaryController)App.fxmlLoader.getController(); 
           jugadorSeleccionado = new Jugador();
           secondaryController.setJugador(jugadorSeleccionado, true);
        }catch(IOException ex){
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
                
    }
    
    @FXML
    private void onActionButtonEditar(ActionEvent event) {
        if(jugadorSeleccionado != null) {
            try{
                App.setRoot("secondary");
                SecondaryController secondaryController = (SecondaryController)App.fxmlLoader.getController(); 
                secondaryController.setJugador(jugadorSeleccionado, false);
                System.out.println(jugadorSeleccionado.getIdJugad());
            }catch(IOException ex){
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }
        
                
   }

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        if (jugadorSeleccionado != null) {
            jugadorSeleccionado.setNombre(textAreaNombre.getText());
            jugadorSeleccionado.setApellidos(textAreaApellidos.getText());
            App.em.getTransaction().begin();
            App.em.merge(jugadorSeleccionado);
            App.em.getTransaction().commit();
            
            int numFilaSeleccionada = tableViewJugadores.getSelectionModel().getSelectedIndex();
            tableViewJugadores.getItems().set(numFilaSeleccionada, jugadorSeleccionado);
            TablePosition pos = new TablePosition(tableViewJugadores, numFilaSeleccionada, null);
            tableViewJugadores.getFocusModel().focus(pos);
            tableViewJugadores.requestFocus();
        }
    }

    @FXML
    private void onActionButtonBuscar(ActionEvent event) {
        if (!textFieldBuscar.getText().isEmpty()){
            if(checkBoxCoincide.isSelected()){
                Query queryJugadorFindAll = App.em.createNamedQuery("Jugador.findByNombre");
                queryJugadorFindAll.setParameter("nombre", textFieldBuscar.getText());
                List<Jugador> listJugador = queryJugadorFindAll.getResultList();
                tableViewJugadores.setItems(FXCollections.observableArrayList(listJugador));
            } else {
                String strQuery = "SELECT * FROM Jugador WHERE LOWER(nombre) LIKE ";
                strQuery += "\'%" + textFieldBuscar.getText().toLowerCase() + "%\'";
                Query queryJugadorLikeNombre = App.em.createNativeQuery(strQuery, Jugador.class);

                List<Jugador> listJugador = queryJugadorLikeNombre.getResultList();
                tableViewJugadores.setItems(FXCollections.observableArrayList(listJugador));

                Logger.getLogger(this.getClass().getName()).log(Level.INFO, strQuery);
            
            }
            
        } else {
            cargarTodosJugadores();
        }
    }

}