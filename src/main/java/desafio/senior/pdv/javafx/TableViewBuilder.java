package desafio.senior.pdv.javafx;

import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

// TODO tornar geração de tableas baseadas em entidades ou DTOs mapeados com anotações.
public class TableViewBuilder<T> {
	
	private TableView<T> tableView;
	private ResourceBundle resourceBundle;
	
	public TableViewBuilder(TableView<T> tableView, ResourceBundle resourceBundle) {
		this.tableView = tableView;
		this.resourceBundle = resourceBundle;
		this.tableView.getColumns().clear();
		this.tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	public TableColumnBuilder<T> coluna(String campo) {
		return new TableColumnBuilder<>(this, campo);
	}
	
	public TableView<T> finalizar() {
		return this.tableView;
	}
	
	public static class TableColumnBuilder<T> {
		
		private final String nome;
		private final TableColumn<T, String> coluna;
		private final TableViewBuilder<T> tableViewBuilder;
		private final Double totalLarguraTabela;
		
		private TableColumnBuilder(TableViewBuilder<T> tableViewBuilder, String nomeColuna) {
			this.nome = nomeColuna;
			this.tableViewBuilder = tableViewBuilder;
			this.coluna = new TableColumn<>(tableViewBuilder.resourceBundle.getString(nomeColuna));
			this.totalLarguraTabela = tableViewBuilder.tableView.getPrefWidth() - 5;
		}
		
		public TableColumnBuilder<T> comLargura(Double larguraEmPorcentagem) {
			Double largura = totalLarguraTabela * larguraEmPorcentagem / 100;
			coluna.setMinWidth(largura);
			coluna.setPrefWidth(largura);
			return this;
		}
		
		public TableColumnBuilder<T> comPropriedadeIgualNome() {
			coluna.setCellValueFactory(new PropertyValueFactory<>(nome));
			return this;
		}
		
		public TableColumnBuilder<T> comPropriedade(String nomeCampo) {
			coluna.setCellValueFactory(new PropertyValueFactory<>(nomeCampo));
			return this;
		}
		
		public TableColumnBuilder<T> comPropriedade(Function<T, String> funcaoToString) {
			coluna.setCellValueFactory(callback -> {
				String valor = funcaoToString.apply(callback.getValue());
				return new SimpleStringProperty(valor);
			});
			return this;
		}
		
		@SuppressWarnings("unchecked")
		public TableColumnBuilder<T> comBotaoAcao(String chaveTexto, BiConsumer<ActionEvent, T> biConsumer) {
			String textoBotao = tableViewBuilder.resourceBundle.getString(chaveTexto);
			coluna.setCellFactory(param -> new ButtonTableCell<>(textoBotao, evento -> {
				Button botao = ( Button ) evento.getTarget();
				StackPane stackPane = ( StackPane ) botao.getParent();
				TableCell<?, ?> tableCell = ( TableCell<?, ?> ) stackPane.getParent();
				TableRow<T> tableRow = ( TableRow<T> ) tableCell.getParent();
				T item = tableRow.getItem();
				biConsumer.accept(evento, item);
			}));
			return this;
		}
		
		public TableViewBuilder<T> finalizar() {
			tableViewBuilder.tableView.getColumns().add(coluna);
			return this.tableViewBuilder;
		}
		
		public TableColumnBuilder<T> comPropriedadeI18n() {
			coluna.setCellValueFactory(new PropertyValueFactory<>(nome) {
				@Override
				public ObservableValue<String> call(TableColumn.CellDataFeatures<T, String> param) {
					ObservableValue<String> value = super.call(param);
					if (value.getValue() == null) {
						return value;
					}
					String valorString = String.valueOf(value.getValue());
					return new SimpleStringProperty(tableViewBuilder.resourceBundle.getString(valorString));
				}
			});
			return this;
		}
		
	}
	
}
