package net.vyky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

    @Column(name = "deleted_by_author")
    private Boolean deletedByAuthor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "parent", "author" }, allowSetters = true)
    private Message parent;

    @ManyToOne(fetch = FetchType.LAZY)
    private VykyUser author;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Message id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public Message content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Message createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getDeletedAt() {
        return this.deletedAt;
    }

    public Message deletedAt(ZonedDateTime deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(ZonedDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Boolean getDeletedByAuthor() {
        return this.deletedByAuthor;
    }

    public Message deletedByAuthor(Boolean deletedByAuthor) {
        this.setDeletedByAuthor(deletedByAuthor);
        return this;
    }

    public void setDeletedByAuthor(Boolean deletedByAuthor) {
        this.deletedByAuthor = deletedByAuthor;
    }

    public Message getParent() {
        return this.parent;
    }

    public void setParent(Message message) {
        this.parent = message;
    }

    public Message parent(Message message) {
        this.setParent(message);
        return this;
    }

    public VykyUser getAuthor() {
        return this.author;
    }

    public void setAuthor(VykyUser vykyUser) {
        this.author = vykyUser;
    }

    public Message author(VykyUser vykyUser) {
        this.setAuthor(vykyUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return getId() != null && getId().equals(((Message) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", deletedByAuthor='" + getDeletedByAuthor() + "'" +
            "}";
    }
}
