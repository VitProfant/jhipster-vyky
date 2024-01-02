package net.vyky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MessageRecipientAttrs.
 */
@Entity
@Table(name = "message_recipient_attrs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MessageRecipientAttrs implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "parent", "author" }, allowSetters = true)
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY)
    private VykyUser recipient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MessageRecipientAttrs id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsRead() {
        return this.isRead;
    }

    public MessageRecipientAttrs isRead(Boolean isRead) {
        this.setIsRead(isRead);
        return this;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public MessageRecipientAttrs isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Message getMessage() {
        return this.message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public MessageRecipientAttrs message(Message message) {
        this.setMessage(message);
        return this;
    }

    public VykyUser getRecipient() {
        return this.recipient;
    }

    public void setRecipient(VykyUser vykyUser) {
        this.recipient = vykyUser;
    }

    public MessageRecipientAttrs recipient(VykyUser vykyUser) {
        this.setRecipient(vykyUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageRecipientAttrs)) {
            return false;
        }
        return getId() != null && getId().equals(((MessageRecipientAttrs) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageRecipientAttrs{" +
            "id=" + getId() +
            ", isRead='" + getIsRead() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}
