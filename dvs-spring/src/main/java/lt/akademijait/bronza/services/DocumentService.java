package lt.akademijait.bronza.services;

import lombok.extern.slf4j.Slf4j;
import lt.akademijait.bronza.dto.document.DocumentCreateCommand;
import lt.akademijait.bronza.dto.document.DocumentGetCommand;
import lt.akademijait.bronza.dto.document.DocumentSetStateCommand;
import lt.akademijait.bronza.dto.document.DocumentUpdateCommand;
import lt.akademijait.bronza.entities.Document;
import lt.akademijait.bronza.entities.DocumentType;
import lt.akademijait.bronza.entities.User;
import lt.akademijait.bronza.entities.UserGroup;
import lt.akademijait.bronza.enums.DocumentState;
import lt.akademijait.bronza.repositories.DocumentRepository;
import lt.akademijait.bronza.repositories.DocumentTypeRepository;
import lt.akademijait.bronza.repositories.UserGroupRepository;
import lt.akademijait.bronza.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentTypeRepository documentTypeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserGroupRepository userGroupRepository;

    //private  final static Logger logger = LoggerFactory.getLogger(DocumentService.class);
    //private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    //GET ALL DOCUMENTS V1 (by P.C.) ====================================================================================
    @Transactional(readOnly = true)
    public List<DocumentGetCommand> getAllDocuments() {
        log.info("Gotten all documents");

        List<DocumentGetCommand> allDocuments = new ArrayList<>();
        for (Document document : documentRepository.findAll()) {
            String reviewerUsername = null;
            if (document.getReviewer() != null) {
                reviewerUsername = document.getReviewer().getUsername();
            }
            allDocuments.add(new DocumentGetCommand(
                    document.getId(),
                    document.getAuthor().getUsername(),
                    document.getDocumentState().toString(),
                    document.getDocumentType().getTitle(),
                    document.getTitle(),
                    document.getDescription(),
                    document.getCreationDate(),
                    document.getSubmissionDate(),
                    document.getConfirmationDate(),
                    document.getRejectionDate(),
                    reviewerUsername,
                    document.getRejectionReason(),
                    document.getPath(),
                    document.getAttachments()));
        }


//        return documentRepository.findAll()
//                .stream()
//                .map((document) -> new DocumentGetCommand(
//                        document.getId(),
//                        document.getAuthor().getUsername(),
//                        document.getDocumentState().toString(),
//                        document.getDocumentType().getTitle(),
//                        document.getTitle(),
//                        document.getDescription(),
//                        document.getCreationDate(),
//                        document.getSubmissionDate(),
//                        document.getConfirmationDate(),
//                        document.getRejectionDate(),
//
//                        document.getReviewer().getUsername(),
//                        document.getRejectionReason(),
//                        document.getPath(),
//                        document.getAttachments()
//                )).collect(Collectors.toList());
        return allDocuments;


    }
*/
    //if there is a need to filter:
    // version A: insert if (username == current userName) --> then do some action
    // version B: allDocuments.filter()
    // version C: use ternary if operator (line 151) and optional use
    // look in DocumentGetCommand, line 59-63 (separate method getReviewer() );


    //GET ALL DOCUMENTS V2 (by G.G.) ===================================================================================
    @Transactional(readOnly = true)
    public List<DocumentGetCommand> getAllDocuments() {
        log.info("Gotten all documents");
        return documentRepository.findAll()
                .stream()
                .map((document) -> new DocumentGetCommand(
                        document.getId(),
                        document.getAuthor().getUsername(),
                        document.getDocumentState().toString(),
                        document.getDocumentType().getTitle(),
                        document.getTitle(),
                        document.getDescription(),
                        document.getCreationDate(),
                        document.getSubmissionDate(),
                        document.getConfirmationDate(),
                        document.getRejectionDate(),
                        document.getReviewer() != null ? document.getReviewer().getUsername() : null,
                        document.getRejectionReason(),
                        document.getPath(),
                        document.getAttachments()
                )).collect(Collectors.toList());
    }

    /*
    //GET BY DOCUMENT_ID V1 (by P.C.) ========================================================================
    @Transactional(readOnly = true)
    public DocumentGetCommand getDocumentById(Long id) {
        Document document = documentRepository.findById(id).orElse(null);
        log.info("Gotten all documents by this id: " + id);
        //logger.error("null "); try/catch; luzimo pvz: skirtingi duomenu tipai (Long ir string)
        String reviewerUsername = null;
        if (document.getReviewer() != null) {
            reviewerUsername = document.getReviewer().getUsername();
        }
        return new DocumentGetCommand(
                document.getId(),
                document.getAuthor().getUsername(),
                document.getDocumentState().toString(),
                document.getDocumentType().getTitle(),
                document.getTitle(),
                document.getDescription(),
                document.getCreationDate(),
                document.getSubmissionDate(),
                document.getConfirmationDate(),
                document.getRejectionDate(),
                reviewerUsername, document.getRejectionReason(),
                document.getPath(),
                document.getAttachments()
        );
    }
*/

    //GET BY DOCUMENT_ID V2 (by G.G.) ========================================================================
    @Transactional(readOnly = true)
    public DocumentGetCommand getDocumentById(Long id) {
        Document document = documentRepository.findById(id).orElse(null);
        log.info("Gotten all documents by this id: " + id);
        //logger.error("null "); try/catch; luzimo pvz: skirtingi duomenu tipai (Long ir string)
        return new DocumentGetCommand(
                document.getId(),
                document.getAuthor().getUsername(),
                document.getDocumentState().toString(),
                document.getDocumentType().getTitle(),
                document.getTitle(),
                document.getDescription(),
                document.getCreationDate(),
                document.getSubmissionDate(),
                document.getConfirmationDate(),
                document.getRejectionDate(),
                document.getReviewer() != null ? document.getReviewer().getUsername() : null,
                document.getRejectionReason(),
                document.getPath(),
                document.getAttachments()
        );
    }

    //GET BY STATE (SUBMITTED) (with filter) ===========================================================================
    @Transactional(readOnly = true)
    public List<DocumentGetCommand> getSubmittedDocuments() {
        log.info("Gotten all documents by this state: " + DocumentState.SUBMITTED);
        return documentRepository.findAll()
                .stream()
                .filter(document -> document.getDocumentState().equals(DocumentState.SUBMITTED))
                .map((document) -> new DocumentGetCommand(
                        document.getId(),
                        //document.getAuthor(),
                        document.getAuthor().getUsername(),
                        document.getDocumentState().toString(),
                        document.getDocumentType().getTitle(),
                        document.getTitle(),
                        document.getDescription(),
                        document.getCreationDate(),
                        document.getSubmissionDate(),
                        document.getConfirmationDate(),
                        document.getRejectionDate(),
                        document.getReviewer() != null ? document.getReviewer().getUsername() : null,
                        document.getRejectionReason(),
                        document.getPath(),
                        document.getAttachments()
                )).collect(Collectors.toList());
    }

    //GET BY STATE (CONFIRMED) (with filter) ===========================================================================
    @Transactional(readOnly = true)
    public List<DocumentGetCommand> getConfirmedDocuments() {
        log.info("Gotten all documents by this state: " + DocumentState.CONFIRMED);
        return documentRepository.findAll()
                .stream()
                .filter(document -> document.getDocumentState().equals(DocumentState.CONFIRMED))
                .map((document) -> new DocumentGetCommand(
                        document.getId(),
                        document.getAuthor().getUsername(),
                        document.getDocumentState().toString(),
                        document.getDocumentType().getTitle(),
                        document.getTitle(),
                        document.getDescription(),
                        document.getCreationDate(),
                        document.getSubmissionDate(),
                        document.getConfirmationDate(),
                        document.getRejectionDate(),
                        document.getReviewer() != null ? document.getReviewer().getUsername() : null,
                        document.getRejectionReason(),
                        document.getPath(),
                        document.getAttachments()
                )).collect(Collectors.toList());
    }

    //GET BY STATE (REJECTED) (with filter) ============================================================================
    @Transactional(readOnly = true)
    public List<DocumentGetCommand> getRejectedDocuments() {
        log.info("Gotten all documents by this state: " + DocumentState.REJECTED);
        return documentRepository.findAll()
                .stream()
                .filter(document -> document.getDocumentState().equals(DocumentState.REJECTED))
                .map((document) -> new DocumentGetCommand(
                        document.getId(),
                        document.getAuthor().getUsername(),
                        document.getDocumentState().toString(),
                        document.getDocumentType().getTitle(),
                        document.getTitle(),
                        document.getDescription(),
                        document.getCreationDate(),
                        document.getSubmissionDate(),
                        document.getConfirmationDate(),
                        document.getRejectionDate(),
                        document.getReviewer() != null ? document.getReviewer().getUsername() : null,
                        document.getRejectionReason(),
                        document.getPath(),
                        document.getAttachments()
                )).collect(Collectors.toList());
    }

    //GET BY STATE (SPECIFIED) (with filter) ===========================================================================
    @Transactional(readOnly = true)
    public List<DocumentGetCommand> getAllDocumentsByDocumentState(DocumentState documentState) {
        log.info("Gotten all documents by this state: " + documentState);
        return documentRepository.findAll()
                .stream()
                .filter(document -> document.getDocumentState().equals(documentState))
                .map((document) -> new DocumentGetCommand(
                        document.getId(),
                        document.getAuthor().getUsername(),
                        document.getDocumentState().toString(),
                        document.getDocumentType().getTitle(),
                        document.getTitle(),
                        document.getDescription(),
                        document.getCreationDate(),
                        document.getSubmissionDate(),
                        document.getConfirmationDate(),
                        document.getRejectionDate(),
                        document.getReviewer() != null ? document.getReviewer().getUsername() : null,
                        document.getRejectionReason(),
                        document.getPath(),
                        document.getAttachments()
                )).collect(Collectors.toList());
    }

    //GET BY TYPE. Version_01 (passing object) =========================================================================
    @Transactional(readOnly = true)
    public List<DocumentGetCommand> getAllDocumentsByDocumentType1(DocumentType documentType) {
        log.info("Gotten all documents by this type: " + documentType);
        return documentRepository.findAll()
                .stream()
                .filter(document -> document.getDocumentType().equals(documentType))
                //.filter(document -> document.getDocumentType().getTitle().equals(documentType.getTitle()))
                .map((document) -> new DocumentGetCommand(
                        document.getId(),
                        document.getAuthor().getUsername(),
                        document.getDocumentState().toString(),
                        document.getDocumentType().getTitle(),
                        document.getTitle(),
                        document.getDescription(),
                        document.getCreationDate(),
                        document.getSubmissionDate(),
                        document.getConfirmationDate(),
                        document.getRejectionDate(),
                        document.getReviewer() != null ? document.getReviewer().getUsername() : null,
                        document.getRejectionReason(),
                        document.getPath(),
                        document.getAttachments()
                )).collect(Collectors.toList());
    }

    //GET BY TYPE. Version_02 (passing String) =========================================================================
    @Transactional(readOnly = true)
    public List<DocumentGetCommand> getAllDocumentsByDocumentType2(String documentTypeTitle) {
        log.info("Gotten all documents by this type: " + documentTypeTitle);
        return documentRepository.findAll()
                .stream()
                .filter(document -> document.getDocumentType().equals(documentTypeRepository.findByTitle(documentTypeTitle)))
                .map((document) -> new DocumentGetCommand(
                        document.getId(),
                        document.getAuthor().getUsername(),
                        document.getDocumentState().toString(),
                        document.getDocumentType().getTitle(),
                        document.getTitle(),
                        document.getDescription(),
                        document.getCreationDate(),
                        document.getSubmissionDate(),
                        document.getConfirmationDate(),
                        document.getRejectionDate(),
                        document.getReviewer() != null ? document.getReviewer().getUsername() : null,
                        document.getRejectionReason(),
                        document.getPath(),
                        document.getAttachments()
                )).collect(Collectors.toList());
    }

    //GET BY TYPE (SPECIFIED) AND BY AUTHOR (SPECIFIED) (passing String) =========================================
    @Transactional(readOnly = true)
    public List<DocumentGetCommand> getAllDocumentsByDocumentTypeAndUsername(String username, String documentTypeTitle) {

        log.info("Gotten all documents by this type: " + documentTypeTitle + " for user " + username);
        return documentRepository.findAll()
                .stream()
                .filter(document -> document.getAuthor().equals(userRepository.findByUsername(username)))
                .filter(document -> document.getDocumentType().equals(documentTypeRepository.findByTitle(documentTypeTitle)))
                .map((document) -> new DocumentGetCommand(
                        document.getId(),
                        document.getAuthor().getUsername(),
                        document.getDocumentState().toString(),
                        document.getDocumentType().getTitle(),
                        document.getTitle(),
                        document.getDescription(),
                        document.getCreationDate(),
                        document.getSubmissionDate(),
                        document.getConfirmationDate(),
                        document.getRejectionDate(),
                        document.getReviewer() != null ? document.getReviewer().getUsername() : null,
                        document.getRejectionReason(),
                        document.getPath(),
                        document.getAttachments()
                )).collect(Collectors.toList());
    }

    //GET BY AUTHOR_ID =================================================================================================
    // (with filter and with filter of permissions (which documents this UserGroup can manage)
    @Transactional(readOnly = true)
    public List<DocumentGetCommand> getAllDocumentsByAuthorId(Long authorId) {
        log.info("Gotten all documents by this author id: " + authorId);
        return documentRepository.findAll()
                .stream()
                .filter(document -> document.getAuthor().getId().equals(authorId))
                .map((document) -> new DocumentGetCommand(
                        document.getId(),
                        document.getAuthor().getUsername(),
                        document.getDocumentState().toString(),
                        document.getDocumentType().getTitle(),
                        document.getTitle(),
                        document.getDescription(),
                        document.getCreationDate(),
                        document.getSubmissionDate(),
                        document.getConfirmationDate(),
                        document.getRejectionDate(),
                        document.getReviewer() != null ? document.getReviewer().getUsername() : null,
                        document.getRejectionReason(),
                        document.getPath(),
                        document.getAttachments()
                )).collect(Collectors.toList());
    }

    //CREATE ===========================================================================================================
    @Transactional
    public void createDocument(DocumentCreateCommand documentCreateCommand) {

        Document newDocument = new Document();
        newDocument.setCreationDate(new Date());

        User user = userRepository.findByUsername(documentCreateCommand.getUsername());
        if (user == null) {
            log.error("User not found (when trying to create document");
            throw new ResourceNotFoundException("My dear Friend, you entered not existing User (you should create that User first) !");
        } else {
            newDocument.setAuthor(user);
        }


        DocumentType documentType = documentTypeRepository.findByTitle(documentCreateCommand.getDocumentTypeTitle());
        if (documentType == null) {
            log.error("DocumentType not found (when trying to create document");
            throw new ResourceNotFoundException("My dear Friend, you entered not existing DocumentType (you should create that DocumentType first) !");
        } else {
            newDocument.setDocumentType(documentType);
        }

        newDocument.setTitle(documentCreateCommand.getTitle());
        newDocument.setDescription(documentCreateCommand.getDescription());
        newDocument.setDocumentState(DocumentState.CREATED);
        documentRepository.save(newDocument);
        log.info("New document created - {} Everything is OK", newDocument.toString());
    }

    //SET DOCUMENT STATE. Version_02 (by J.C.) =========================================================================
    @Transactional
    public void setDocumentStateV2(DocumentSetStateCommand documentSetStateCommand) {

        //User user = userRepository.findByUsername(documentSetStateCommand.getAuthorUsername());
        Document documentToSetState = documentRepository.getOne(documentSetStateCommand.getDocumentId());
        User reviewerUser = userRepository.findByUsername(documentSetStateCommand.getReviewerUsername());

        // checking if user can set state:
        Set<UserGroup> userGroupsBelongingToUser = reviewerUser.getUserGroups();

        boolean canSubmit = false;
        boolean canReview = false;
        for (UserGroup userGroup : userGroupsBelongingToUser) {

            //patikrinimas ar grupei yra priskirtas tas dokumento tipas, kuriam planuojama keisti bukle
            if (userGroup.getReviewDocumentType().contains(documentToSetState.getDocumentType())) {
                log.info("Yes, this UserGroup can REVIEW this type of document");
                canReview = true;
            } else if (userGroup.getSubmissionDocumentType().contains(documentToSetState.getDocumentType()) &&
                    reviewerUser.getUsername().equals(documentToSetState.getAuthor().getUsername())) {
                log.info("Yes, this UserGroup can (only) SUBMITT this type of document");
                canSubmit = true;

                //patikrinimas ar Revieweris egzistuoja apskritai (neveikia kazkodel)
            } else {
                log.info("Document can be submitted only by its Author. Also by Reviewer");
                throw new ResourceNotFoundException("Document can be submitted only by its Author. Also by Reviewer)");
            }


            //jei esama state yra REJECTED arba ACCEPTED  tai nustatyti kad keisti bukles nebegalima (pvz. iseiti is metodo)
            //arba nustatyti kad galima keisti tik jei esama bukle yra CONFIRMED

            //jei esama state yra CREATED, o siuloma bukle nera SUBMITED tai nustatyti kad keisti bukles nebegalima (pvz. iseiti is metodo)
            ///arba nustatyti kad galima keisti tik jei siuloma bukle yra CONFIRMED

            //jei yra SUBMITED, o siuloma bukle nera REJECTED || CONFIRMED tai nustatyti kad keisti bukles nebegalima (pvz. iseiti is metodo;


            // setting checked user as reviewer:
            if (canSubmit) {
                switch (documentSetStateCommand.getDocumentState()) {
                    case "CREATED": {
                        log.info("Case1. Not proceeded. State CREATED already set during document creation");
                        throw new ResourceNotFoundException("Case1. Not proceeded. State CREATED already set during document creation");
                    }
                    case "SUBMITTED": {
                        if (documentToSetState.getDocumentState().equals(DocumentState.CREATED)) {
                            documentToSetState.setDocumentState(DocumentState.SUBMITTED);
                            documentToSetState.setSubmissionDate(new Date());
                            break;
                        } else {
                            log.info("Case2. Not proceeded. Only CREATED document can be set to SUBMITTED");
                            throw new ResourceNotFoundException("C2. Not proceeded. Only CREATED document can be set to SUBMITTED");
                        }
                    }
                    default:
                        throw new ResourceNotFoundException("Not proceeded. Only CREATED, SUBMITTED allowed.");
                }
                documentRepository.save(documentToSetState);
                log.info("Document state set to: " + documentSetStateCommand.getDocumentState());

            } else if (canReview) {
                switch (documentSetStateCommand.getDocumentState()) {
                    case "CREATED": {
                        log.info("Case3. Not proceeded. State CREATED already set during document creation");
                        throw new ResourceNotFoundException("Case3. Not proceeded. State CREATED already set during document creation");
                    }
                    case "SUBMITTED": {
                        if (documentToSetState.getDocumentState().equals(DocumentState.CREATED)) {
                            documentToSetState.setDocumentState(DocumentState.SUBMITTED);
                            documentToSetState.setSubmissionDate(new Date());
                            break;
                        } else {
                            log.info("Case4. Not proceeded. Only CREATED document can be set to SUBMITTED");
                            throw new ResourceNotFoundException("Case4. Not proceeded. Only CREATED document can be set to SUBMITTED");
                        }
                    }
                    case "REJECTED": {
                        if (documentToSetState.getDocumentState().equals(DocumentState.SUBMITTED)) {
                            documentToSetState.setReviewer(reviewerUser);
                            documentToSetState.setDocumentState(DocumentState.REJECTED);
                            documentToSetState.setRejectionDate(new Date());
                            documentToSetState.setRejectionReason(documentSetStateCommand.getRejectionReason());
                            break;
                        } else {
                            log.info("Case5. Not proceeded. Only SUBMITTED document can be reviewed");
                            throw new ResourceNotFoundException("Case5. Not proceeded. Only SUBMITTED document can be reviewed");
                        }
                    }
                    case "CONFIRMED": {
                        if (documentToSetState.getDocumentState().equals(DocumentState.SUBMITTED)) {
                            documentToSetState.setReviewer(reviewerUser);
                            documentToSetState.setDocumentState(DocumentState.CONFIRMED);
                            documentToSetState.setConfirmationDate(new Date());
                            break;
                        } else {
                            log.info("Case6. Not proceeded. Only SUBMITTED document can be reviewed");
                            throw new ResourceNotFoundException("Case6. Not proceeded. Only SUBMITTED document can be reviewed");
                        }
                    }
                    default:
                        throw new ResourceNotFoundException("Not proceeded. Only CREATED, SUBMITTED, CONFIRMED, REJECTED allowed");
                }
                documentRepository.save(documentToSetState);
                log.info("Document state set to: " + documentSetStateCommand.getDocumentState());
            }
        }
    }

    //UPDATE. Version_02. ==============================================================================================
    @Transactional
    public void updateDocument(Long id, DocumentUpdateCommand documentUpdateCommand) {
        Document documentToUpdate = documentRepository.findById(id).orElse(null);

        DocumentType documentType = documentTypeRepository.findByTitle(documentUpdateCommand.getDocumentTypeTitle());
        documentToUpdate.setDocumentType(documentType);


        documentToUpdate.setTitle(documentUpdateCommand.getTitle());
        documentToUpdate.setDescription(documentUpdateCommand.getDescription());
        documentRepository.save(documentToUpdate);
        log.info("Document data updated - {} Everything is OK" + documentToUpdate.toString());
    }

    //DELETE ===========================================================================================================
    @Transactional
    public void deleteDocument(long id) {
        documentRepository.deleteById(id);
        log.info("Document with id = " + id + " was deleted");
    }

/*
    // commented as not necessary (?);
    // dar reikia paduoti username kad patikrinti ar jis turi permission'a
    // tada paduoti setDocumentState
    //ASSIGN DOCUMENT_TYPE TO DOCUMENT =================================================================================
    @Transactional
    public void assignDocumentTypeToDocument(Long id, String title) {
        //DocumentType documentType = documentTypeRepository.findById(id).orElseThrow(null);
        DocumentType documentType = documentTypeRepository.findByTitle(title);
        Document document = documentRepository.findById(id).orElse(null);
        if (documentType == null) {
            throw new ResourceNotFoundException("My dear Friend, you entered not existing DocumentType (you should create that DocumentType first) !");
        } else {
            //documentType.getDocuments().add(document); //
            document.setDocumentType(documentType); //jei norim pakeisti tai tiesiog settini is naujo (.remove nereikia).
        }
    }

    //DE-ASSIGN DOCUMENT_TYPE TO DOCUMENT ==============================================================================
    @Transactional
    public void deassignDocumentTypeToDocument(Long id, String title) {
        //DocumentType documentType = documentTypeRepository.findById(id).orElseThrow(null);
        DocumentType documentType = documentTypeRepository.findByTitle(title);
        Document document = documentRepository.findById(id).orElse(null);
        if (documentType == null) {
            throw new ResourceNotFoundException("My dear Friend, you entered not existing DocumentType (you should create that DocymentType first) !");
        } else {
            documentType.getDocuments().remove(document);
        }
    }
*/

}
