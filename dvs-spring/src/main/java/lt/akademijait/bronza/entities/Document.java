package lt.akademijait.bronza.entities;


import lt.akademijait.bronza.enums.DocumentState;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="DOCUMENT")
//@Table(name="DOCUMENT", uniqueConstraints = {@UniqueConstraint(columnNames = "title")})
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //private String id;

    @ElementCollection
    @CollectionTable
    @Column
    private List<String> additionalFilePaths = new ArrayList<>();

    @OneToMany
    private List<Attachment> attachments;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    @Column
    private DocumentState documentState;

    @ManyToOne
    @JoinColumn(name="doctype_id")
    private DocumentType documentType;

    @Column (nullable = false)
    private String title;

    @Column (nullable = false)
    private String description;

    @Column
    private Date creationDate;

    @Column
    private Date submissionDate;

    @Column
    private Date confirmationDate;

    @Column
    private Date rejectionDate;

    @ManyToOne
    private User reviewer;

    @Column
    private String rejectionReason;

    @Column
    private String path;


    public Document() {
    }

    public Document(User author, DocumentState documentState, DocumentType documentType, String title,
                    String description, Date creationDate, Date submissionDate, Date confirmationDate,
                    Date rejectionDate, User reviewer, String rejectionReason, String path,
                    List<Attachment> attachments) {
        this.author = author;
        this.documentState = documentState;
        this.documentType = documentType;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.submissionDate = submissionDate;
        this.confirmationDate = confirmationDate;
        this.rejectionDate = rejectionDate;
        this.reviewer = reviewer;
        this.rejectionReason = rejectionReason;
        this.path = path;
        this.attachments = attachments;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getAdditionalFilePaths() {
        return additionalFilePaths;
    }

    public void setAdditionalFilePaths(List<String> additionalFilePaths) {
        this.additionalFilePaths = additionalFilePaths;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public DocumentState getDocumentState() {
        return documentState;
    }

    public void setDocumentState(DocumentState documentState) {
        this.documentState = documentState;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
//        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
//        this.creationDate = DATE_FORMAT.format(creationDate); // incompatible types (String and object)
        this.creationDate = creationDate;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Date getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Date confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public Date getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(Date rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public User getReviewer() {
        return reviewer;
    }

    //  for preventing NullPointerException ir case then "document.getReviewer() == null"
//    public User getReviewer() {
//        if (this.reviewer == null) {
//            return new User();
//        } else {
//            return reviewer;
//        }
//    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override // this is for formatting output of logger
    public String toString() {
        return "Document{" +
                "documentState=" + documentState +
                ", documentType=" + documentType +
                ", title='" + title + '\'' +
                '}';
    }

}



