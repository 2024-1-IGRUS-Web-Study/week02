import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean loanInfo;
    private Loan recentLoan;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.loanInfo = false;
        this.recentLoan = null;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isLoanInfo() {
        return loanInfo;
    }

    public void setLoanInfo(boolean loanInfo) {
        this.loanInfo = loanInfo;
    }

    public Loan getRecentLoan() {
        return recentLoan;
    }

    public void setRecentLoan(Loan recentLoan) {
        this.recentLoan = recentLoan;
    }
}

class Loan {
    private int loanId;
    private String borrower;
    private Book book;
    private int loanDate;
    private int returnDate;

    public Loan(int loanId, String borrower, Book book, int loanDate, int returnDate) {
        this.loanId = loanId;
        this.borrower = borrower;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public String getBorrower() {
        return borrower;
    }

    public Book getBook() {
        return book;
    }

    public int getLoanDate() {
        return loanDate;
    }

    public int getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(int returnDate) {
        this.returnDate = returnDate;
    }
}

public class BookLendingSystem {
    private List<Book> books;
    private List<Loan> loans;
    private int nextLoanId;

    public BookLendingSystem() {
        this.books = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.nextLoanId = 1;
    }

    public void addBook(int bookId, String title, String author) {
        Book book = new Book(bookId, title, author);
        books.add(book);
        System.out.println("도서가 등록되었습니다.");
    }

    public void displayBooks() {
        System.out.println("도서 목록:");
        for (Book book : books) {
            System.out.print("도서 ID: "
                    + book.getBookId() + ", 제목: "
                    + book.getTitle() + ", 저자: "
                    + book.getAuthor()
                    + ", 대출 여부: ");
            if (book.isLoanInfo()) {
                System.out.println("대출 중");
            }
            else {
                System.out.println("대출 가능");
            }
        }
    }

    public void lendBook(int bookId, String borrower, int loanId, int loanDate) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                if (!book.isLoanInfo()) {
                    int returnDate = loanDate + 14; // 14일
                    Loan loan = new Loan(loanId, borrower, book, loanDate, returnDate);
                    loans.add(loan);
                    book.setLoanInfo(true);
                    System.out.println("도서 대출이 완료되었습니다.");
                }
                else {
                    System.out.println("이 도서는 이미 대출 중입니다.");
                }
                return;
            }
        }
        System.out.println("Error : 해당 도서가 존재하지 않습니다.");
    }

    public void returnBook(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                if (book.isLoanInfo()) {
                    for (Loan loan : loans) {
                        if (loan.getBook().getBookId() == bookId) {
                            loan.setReturnDate(loan.getReturnDate());
                            loans.remove(loan);
                            book.setLoanInfo(false);
                            book.setRecentLoan(null);
                            System.out.println("도서가 반납되었습니다.");
                            return;
                        }
                    }
                }
                else {
                    System.out.println("Error : 해당 도서는 현재 대출 중이 아닙니다.");
                    return;
                }
            }
        }
        System.out.println("Error : 해당 도서는 존재하지 않습니다.");
    }

    public void displayLoans(){
        System.out.println("대출 정보:");
        for (Loan loan : loans) {
            System.out.println("대출자 ID: " + loan.getLoanId()
                    + ", 대출자: " + loan.getBorrower()
                    + ", 도서 ID: " + loan.getBook().getBookId()
                    + ", 도서 제목: " + loan.getBook().getTitle()
                    + ", 대출 일자: " + loan.getLoanDate()
                    + ", 반납 일자: " + loan.getReturnDate());
        }
    }

    public static void main(String[] args) {
        BookLendingSystem library = new BookLendingSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("도서 대출 시스템");
            System.out.println("1. 도서 등록");
            System.out.println("2. 도서 목록 출력");
            System.out.println("3. 도서 대출");
            System.out.println("4. 도서 반납");
            System.out.println("5. 대출 정보 확인");
            System.out.println("6. 종료");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("도서 ID를 입력하세요:");
                    int bookId = scanner.nextInt();
                    System.out.println("도서 제목을 입력하세요:");
                    String title = scanner.next();
                    System.out.println("저자를 입력하세요:");
                    String author = scanner.next();
                    library.addBook(bookId, title, author);
                    break;
                case 2:
                    library.displayBooks();
                    break;
                case 3:
                    System.out.println("대출할 도서의 ID를 입력하세요:");
                    int lendBookId = scanner.nextInt();
                    System.out.println("대출자의 이름을 입력하세요:");
                    String borrower = scanner.next();
                    System.out.println("대출자 ID를 입력하세요:");
                    int loanId = scanner.nextInt();
                    System.out.println("대출일을 입력하세요:");
                    int loanDate = scanner.nextInt();
                    library.lendBook(lendBookId, borrower, loanId, loanDate);
                    break;
                case 4:
                    System.out.println("반납할 도서의 ID를 입력하세요:");
                    int returnBookId = scanner.nextInt();
                    library.returnBook(returnBookId);
                    break;
                case 5:
                    library.displayLoans();
                    break;
                case 6:
                    System.out.println("프로그램을 종료합니다.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Error : 잘못된 선택입니다. 다시 선택하세요.");
            }
        }

    }
}
