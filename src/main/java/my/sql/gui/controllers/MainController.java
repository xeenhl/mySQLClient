package my.sql.gui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseEvent;
import my.sql.db.DBHellper;
import my.sql.gui.controllers.model.Element;
import my.sql.gui.model.elements.*;

import java.net.URL;
import java.sql.Types;
import java.util.*;

/**
 * Created by olehkozlovskyi on 07.05.15.
 */
public class MainController implements Initializable{

    @FXML
    private TreeView<Element> dbTree;

    @FXML
    private TableView<Row> table;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        table.setEditable(true);
        dbTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TreeItem<Element> base = new TreeItem<>(new Element("DataBases:", Element.Type.OTHER));
        base.setExpanded(true);

        DBHellper helper = new DBHellper();
        helper.getDBList().stream().forEach( s -> {
            TreeItem<Element> dataBase = new TreeItem<>(new Element(s, Element.Type.DB));
            helper.getBdTables(s).stream().forEach(s1 -> {
                TreeItem<Element> table = new TreeItem<>(new Element(s1, Element.Type.TABLE));
                dataBase.getChildren().add(table);
            });
            base.getChildren().add(dataBase);
        });

        dbTree.setRoot(base);
        dbTree.setCellFactory(param -> new TreeCellImpl());


    }

    @FXML
    public void click(MouseEvent event) {


    }

    private final class TreeCellImpl extends TreeCell<Element> {

        private ContextMenu conMenu = new ContextMenu();
        private Map<String, Type> typeMap = new HashMap<>();

        public TreeCellImpl() {
            MenuItem read100 = new MenuItem("Read last 100 elements");
            read100.setOnAction(event -> {

                table.getColumns().clear();

                Element elm = getTreeItem().getValue();
                if(elm.getType().equals(Element.Type.TABLE)) {
                    DBHellper helper = new DBHellper();

                    Map<String, List<BaseElement>> columns = helper.getTop100FromTable(getTreeItem().getParent().getValue().toString(), elm.toString());
                    ObservableList<Row> rows = FXCollections.observableArrayList();

                    Set<Map.Entry<String, List<BaseElement>>> set = columns.entrySet();

                    int max = ((Map.Entry<String, List<BaseElement>>)set.toArray()[0]).getValue().size();
                    for(int i= 0; i < max; i++) {
                        Row row = new Row();
                        final int index = i;
                        set.stream().forEach(v -> {
                            row.addColumn(v.getKey(), v.getValue().get(index).toString());
                        });
                        rows.add(row);
                    }

                    max = set.size();
                    for(int i= 0; i < max; i++) {
                        final int index = i;
                        final String columnLable = ((Map.Entry<String, List<BaseElement>>)set.toArray()[max-1-index]).getKey();
                        TableColumn<Row, String> column = new TableColumn<>(columnLable);
                        typeMap.put(columnLable, ((Map.Entry<String, List<BaseElement>>)set.toArray()[0]).getValue().get(0).getType());
                        //column.setEditable(true);
                        column.setCellFactory(TextFieldTableCell.forTableColumn());
                        column.setOnEditCommit(ev -> {
                            Row row = ((Row)ev.getTableView().getItems().get(
                                    ev.getTablePosition().getRow()
                            ));

                            if(helper.update(
                                    dbTree.getSelectionModel().getSelectedItem().getValue().toString(),
                                    row.getElements().get("id").getValue(),
                                    columnLable,
                                    generateElement(ev.getNewValue(), columnLable)
                                    )
                            ) {
                                row.updateElement(columnLable, ev.getNewValue());
                            }else{
                                System.out.println(ev.getOldValue());
                                row.updateElement(columnLable, ev.getOldValue());
                                ev.getTableColumn().setVisible(false);
                                ev.getTableColumn().setVisible(true);
                            }
                        });
                        column.setCellValueFactory(cellData -> cellData.getValue().getElements().get(columnLable));
                        table.getColumns().add(column);
                    }

                    table.setItems(rows);
                }

            });
            conMenu.getItems().add(read100);

        }

        @Override
        public void updateItem(Element element, boolean empty) {

            super.updateItem(element, empty);

            if(empty || element == null) {
                setText(null);
                setGraphic(null);
            }else {

                setText(element.toString());
                setGraphic(getTreeItem().getGraphic());
                if(element.getType().equals(Element.Type.TABLE)) {
                    setContextMenu(conMenu);
                }
            }
        }

        private final BaseElement generateElement(String value, String columnLable) {

           Type type = typeMap.get(columnLable);

            switch (type.getSqlType()) {
                case Types.INTEGER:
                    return new IntElement(Integer.valueOf(value));
                case Types.VARCHAR:
                    return new VarCharElement(value);
                default:
                    return null;
            }

        }


    }

}
