
package Task1;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Головний клас для запуску
public class Task1SRP {
    public static void main(String[] args) {
        BookReturnModel book = new BookReturnModel(
            "12345",
            "Ivan Petrenko",
            "ivan@gmail.com",
            "978-123",
            "Java Programming",
            LocalDate.of(2026, 10, 10),
            LocalDate.of(2026, 10, 13)
        );

        LibraryLoanService service = new LibraryLoanService(
            new BookReturnValidator(),
            new ReturnCalculator(),
            new ReturnNoticeFormatter(),
            new CatalogRepository(),
            new EmailNotifier()
        );

        service.processBookReturn(book);
    }
}

// Дані про повернення книги
record BookReturnModel(
    String ticket,
    String reader,
    String email,
    String isbn,
    String title,
    LocalDate expectedDate,
    LocalDate actualDate
) {}

// Перевірка даних
class BookReturnValidator {
    void validate(BookReturnModel book) {
        if (book == null) {
            throw new IllegalArgumentException("Book is null");
        }

        if (book.ticket() == null || book.ticket().isBlank()) {
            throw new IllegalArgumentException("Ticket is required");
        }

        if (book.isbn() == null || book.isbn().isBlank()) {
            throw new IllegalArgumentException("ISBN is required");
        }

        if (book.expectedDate() == null || book.actualDate() == null) {
            throw new IllegalArgumentException("Dates are required");
        }

        if (book.actualDate().isBefore(
                book.expectedDate().minusYears(1))) {
            throw new IllegalStateException("Invalid return date");
        }
    }
}

// Результат обчислення
record ReturnResult(
    long overdueDays,
    BigDecimal fine,
    int points
) {}

// Розрахунок штрафу та бонусів
class ReturnCalculator {
    ReturnResult calculate(BookReturnModel book) {
        long days = ChronoUnit.DAYS.between(
            book.expectedDate(),
            book.actualDate()
        );

        if (days > 0) {
            BigDecimal fine = BigDecimal.valueOf(days)
                .multiply(new BigDecimal("15.00"));

            return new ReturnResult(days, fine, 0);
        }

        return new ReturnResult(days, BigDecimal.ZERO, 10);
    }
}

// Формування повідомлення
class ReturnNoticeFormatter {
    String format(BookReturnModel book, ReturnResult result) {
        String text = "Book: " + book.title()
            + "\nReader: " + book.reader();

        if (result.overdueDays() > 0) {
            text += "\nOverdue days: " + result.overdueDays();
            text += "\nFine: " + result.fine() + " UAH";
        } else {
            text += "\nLoyalty points: " + result.points();
        }

        return text;
    }
}

// Збереження повернення книги
class CatalogRepository {
    void save(BookReturnModel book) {
        String record = book.isbn()
            + "|RETURNED|"
            + book.actualDate()
            + "|By_" + book.ticket()
            + System.lineSeparator();

        try {
            Files.writeString(
                Path.of("library_catalog_history.txt"),
                record,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new IllegalStateException(
                "Cannot save return", e
            );
        }
    }
}

// Імітація надсилання email
class EmailNotifier {
    void send(BookReturnModel book, String message) {
        System.out.println("Email to: " + book.email());
        System.out.println(message);
    }
}

// Клас, який керує всіма операціями
class LibraryLoanService {
    private final BookReturnValidator validator;
    private final ReturnCalculator calculator;
    private final ReturnNoticeFormatter formatter;
    private final CatalogRepository repository;
    private final EmailNotifier notifier;

    LibraryLoanService(
        BookReturnValidator validator,
        ReturnCalculator calculator,
        ReturnNoticeFormatter formatter,
        CatalogRepository repository,
        EmailNotifier notifier
    ) {
        this.validator = validator;
        this.calculator = calculator;
        this.formatter = formatter;
        this.repository = repository;
        this.notifier = notifier;
    }

    void processBookReturn(BookReturnModel book) {
        validator.validate(book);
        ReturnResult result = calculator.calculate(book);
        String message = formatter.format(book, result);
        repository.save(book);
        notifier.send(book, message);
    }
}
