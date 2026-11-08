package task5;

import java.util.Objects;

public class Task5DIP {
    public static void main(String[] args) {
        Document doc = new Document();
        doc.setDocumentId("DOC-2026-001");
        doc.setTitle("Фінансовий звіт Q3");
        doc.setAuthorUsername("sanya_yuriev");
        doc.setApproverUsername("chief_manager");

        System.out.println("=== 1. ТЕСТОВЕ СЕРЕДОВИЩЕ (Mock / In-Memory) ===");
        DocumentSearchIndex mockIndex = new MockDocumentSearchIndex();
        UserValidator mockValidator = new MockUserValidator();
        ApprovalNotifier consoleNotifier = new ConsoleApprovalNotifier();

        DocumentApprovalWorkflow testWorkflow = new DocumentApprovalWorkflow(mockIndex, mockValidator, consoleNotifier);
        testWorkflow.approveDocument(doc);

        System.out.println("\n=== 2. ПРОДАКШН СЕРЕДОВИЩЕ (Production Services) ===");
        DocumentSearchIndex prodIndex = new ElasticsearchDocumentIndex();
        UserValidator prodValidator = new ActiveDirectoryUserValidator();
        ApprovalNotifier prodNotifier = new SlackWebhookNotifier();

        DocumentApprovalWorkflow prodWorkflow = new DocumentApprovalWorkflow(prodIndex, prodValidator, prodNotifier);
        prodWorkflow.approveDocument(doc);
    }
}



class Document {
    private String documentId;
    private String title;
    private String authorUsername;
    private String approverUsername;

    public String getDocumentId() { return documentId; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthorUsername() { return authorUsername; }
    public void setAuthorUsername(String authorUsername) { this.authorUsername = authorUsername; }
    public String getApproverUsername() { return approverUsername; }
    public void setApproverUsername(String approverUsername) { this.approverUsername = approverUsername; }
}



interface DocumentSearchIndex {
    void indexDocument(String docId, String title, String status);
}

interface UserValidator {
    boolean validateUserSignature(String username);
}

interface ApprovalNotifier {
    void notifyApproval(String documentId, String title, String approver);
}



class ElasticsearchDocumentIndex implements DocumentSearchIndex {
    @Override
    public void indexDocument(String docId, String title, String status) {
        System.out.println("[Elasticsearch Index] Документ " + docId + " ('" + title + "') перевиндексовано зі статусом '" + status + "'.");
    }
}

class ActiveDirectoryUserValidator implements UserValidator {
    @Override
    public boolean validateUserSignature(String username) {
        System.out.println("[Active Directory LDAP] Користувача '" + username + "' підтверджено у групі 'Managers'.");
        return true;
    }
}

class SlackWebhookNotifier implements ApprovalNotifier {
    @Override
    public void notifyApproval(String documentId, String title, String approver) {
        System.out.println("[Slack Webhook] Повідомлення у канал #approvals: Документ '" + title + "' (ID: " + documentId + ") підписано користувачем " + approver + ".");
    }
}



class MockDocumentSearchIndex implements DocumentSearchIndex {
    @Override
    public void indexDocument(String docId, String title, String status) {
        System.out.println("[MOCK Index] Індексування документу " + docId + " пройшло в пам'яті.");
    }
}

class MockUserValidator implements UserValidator {
    @Override
    public boolean validateUserSignature(String username) {
        System.out.println("[MOCK AD] Валідація користувача " + username + " успішна.");
        return true;
    }
}

class ConsoleApprovalNotifier implements ApprovalNotifier {
    @Override
    public void notifyApproval(String documentId, String title, String approver) {
        System.out.println("[Console Notifier] Сповіщення: Документ " + documentId + " підписано.");
    }
}



class DocumentApprovalWorkflow {
    private final DocumentSearchIndex searchIndex;
    private final UserValidator adValidator;
    private final ApprovalNotifier notifier;


    public DocumentApprovalWorkflow(DocumentSearchIndex searchIndex, UserValidator adValidator, ApprovalNotifier notifier) {
        this.searchIndex = Objects.requireNonNull(searchIndex, "searchIndex is required");
        this.adValidator = Objects.requireNonNull(adValidator, "adValidator is required");
        this.notifier = Objects.requireNonNull(notifier, "notifier is required");
    }

    public void approveDocument(Document doc) {
        if (doc == null || doc.getDocumentId() == null || doc.getDocumentId().isEmpty()) {
            throw new IllegalArgumentException("Некоректний документ для узгодження.");
        }

        boolean isValidApprover = this.adValidator.validateUserSignature(doc.getApproverUsername());
        if (!isValidApprover) {
            throw new IllegalStateException("Підписант не має повноважень для підпису.");
        }

        this.searchIndex.indexDocument(doc.getDocumentId(), doc.getTitle(), "APPROVED");
        this.notifier.notifyApproval(doc.getDocumentId(), doc.getTitle(), doc.getApproverUsername());
    }
}