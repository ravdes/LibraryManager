package com.mycompany.librarymanagement;

import com.mycompany.db.Book;
import com.mycompany.db.BookDAOImpl;
import com.mycompany.db.EnvParser;
import com.mycompany.db.HibernateConfigManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class LibraryController implements Initializable {
	private final Logger log = LoggerFactory.getLogger(LibraryController.class);
	private final BookDAOImpl dbManager = new BookDAOImpl(
			new HibernateConfigManager(new Configuration(), new EnvParser()));
	@FXML
	public TextField fieldTitle;
	@FXML
	public TextField fieldAuthor;
	@FXML
	public TextField fieldGenre;
	@FXML
	public TextField fieldPages;
	@FXML
	public TextField fieldSearch;
	@FXML
	public Button buttonClear;
	@FXML
	public Button buttonNew;
	@FXML
	public Button buttonInsert;
	@FXML
	public Button buttonUpdate;
	@FXML
	public Button buttonDelete;
	@FXML
	public TableView<Book> tableView;
	@FXML
	public TableColumn<Book, Integer> columnId;
	@FXML
	public TableColumn<Book, String> columnTitle;
	@FXML
	public TableColumn<Book, String> columnAuthor;
	@FXML
	public TableColumn<Book, String> columnGenre;
	@FXML
	public TableColumn<Book, Integer> columnPages;
	private Book currentBook;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		showBooks();
		buttonDelete.setDisable(true);
		buttonUpdate.setDisable(true);
		fieldSearch.textProperty().addListener((ObservableList, oldValue, newValue) -> {

			if (newValue.isEmpty()) {
				showBooks();
			} else {
				searchUtility(newValue);

			}


		});


	}

	private void getCurrentBook() {
		this.currentBook = tableView.getSelectionModel().getSelectedItem();
	}

	public void searchUtility(String queryElement) {

		ObservableList<Book> comparisonList = FXCollections.observableArrayList();

		for (Book bookInLoop : this.dbManager.getAllBooks()) {
			int id = bookInLoop.getId();
			if (queryElement.equals(String.valueOf(id)) || queryElement.equals(String.valueOf(bookInLoop.getPages())) ||
					bookInLoop.getTitle().toLowerCase().contains(queryElement.toLowerCase()) ||
					bookInLoop.getAuthor().toLowerCase().contains(queryElement.toLowerCase()) ||
					bookInLoop.getGenre().toLowerCase().contains(queryElement.toLowerCase())) {

				comparisonList.add(bookInLoop);
			}

			tableView.setItems(comparisonList);
		}

	}

	@FXML

	public void clear() {
		tableView.getSelectionModel().clearSelection();
		fieldTitle.setText("");
		fieldAuthor.setText("");
		fieldGenre.setText("");
		fieldPages.setText("");
		buttonUpdate.setDisable(true);
		buttonDelete.setDisable(true);
	}

	@FXML
	public void mouseAction(MouseEvent mouseEvent) {
		try {
			getCurrentBook();
			this.currentBook = new Book(currentBook.getTitle(), currentBook.getAuthor(), currentBook.getGenre(),
					currentBook.getPages());
			fieldTitle.setText(currentBook.getTitle());
			fieldAuthor.setText(currentBook.getAuthor());
			fieldGenre.setText(currentBook.getGenre());
			fieldPages.setText(String.valueOf(currentBook.getPages()));
			buttonDelete.setDisable(false);
			buttonUpdate.setDisable(false);
		} catch (Exception e) {
			log.error("Error occurred", e);
		}

	}

	@FXML

	public void newTable() {

		this.dbManager.createAndDropNewTable();
		showBooks();


	}

	@FXML
	private void showBooks() {
		String centeredStyle = "-fx-alignment: CENTER;";
		ObservableList<Book> bookList = this.dbManager.getAllBooks();
		columnId.setStyle(centeredStyle);
		columnTitle.setStyle(centeredStyle);
		columnAuthor.setStyle(centeredStyle);
		columnGenre.setStyle(centeredStyle);
		columnPages.setStyle(centeredStyle);
		columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		columnTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
		columnAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
		columnGenre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
		columnPages.setCellValueFactory(new PropertyValueFactory<>("Pages"));
		tableView.setItems(bookList);


	}

	@FXML
	private void addBook() {

		this.dbManager.insertBook(fieldTitle.getText(), fieldAuthor.getText(), fieldGenre.getText(),
				Integer.parseInt(fieldPages.getText()));
		showBooks();
	}


	@FXML
	public void updateBook() {
		getCurrentBook();

		this.dbManager.updateBook(this.currentBook.getId(), fieldTitle.getText(), fieldAuthor.getText(),
				fieldGenre.getText(), Integer.parseInt(fieldPages.getText()));
		showBooks();
		clear();
		buttonDelete.setDisable(true);
		buttonUpdate.setDisable(true);


	}

	@FXML
	public void deleteBook() {
		getCurrentBook();

		this.dbManager.deleteBook(this.currentBook.getId());
		showBooks();
		clear();
		buttonDelete.setDisable(true);
		buttonUpdate.setDisable(true);


	}


}