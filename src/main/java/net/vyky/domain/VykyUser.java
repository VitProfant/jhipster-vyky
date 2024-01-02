package net.vyky.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import net.vyky.domain.enumeration.Role;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VykyUser.
 */
@Entity
@Table(name = "vyky_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VykyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @NotNull
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "messages_read_at")
    private ZonedDateTime messagesReadAt;

    @Column(name = "reactions_read_at")
    private ZonedDateTime reactionsReadAt;

    @Column(name = "last_activity")
    private ZonedDateTime lastActivity;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VykyUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public VykyUser version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getLogin() {
        return this.login;
    }

    public VykyUser login(String login) {
        this.setLogin(login);
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public VykyUser password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public VykyUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return this.role;
    }

    public VykyUser role(Role role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ZonedDateTime getMessagesReadAt() {
        return this.messagesReadAt;
    }

    public VykyUser messagesReadAt(ZonedDateTime messagesReadAt) {
        this.setMessagesReadAt(messagesReadAt);
        return this;
    }

    public void setMessagesReadAt(ZonedDateTime messagesReadAt) {
        this.messagesReadAt = messagesReadAt;
    }

    public ZonedDateTime getReactionsReadAt() {
        return this.reactionsReadAt;
    }

    public VykyUser reactionsReadAt(ZonedDateTime reactionsReadAt) {
        this.setReactionsReadAt(reactionsReadAt);
        return this;
    }

    public void setReactionsReadAt(ZonedDateTime reactionsReadAt) {
        this.reactionsReadAt = reactionsReadAt;
    }

    public ZonedDateTime getLastActivity() {
        return this.lastActivity;
    }

    public VykyUser lastActivity(ZonedDateTime lastActivity) {
        this.setLastActivity(lastActivity);
        return this;
    }

    public void setLastActivity(ZonedDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public VykyUser createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public VykyUser updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ZonedDateTime getDeletedAt() {
        return this.deletedAt;
    }

    public VykyUser deletedAt(ZonedDateTime deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(ZonedDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VykyUser)) {
            return false;
        }
        return getId() != null && getId().equals(((VykyUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VykyUser{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", role='" + getRole() + "'" +
            ", messagesReadAt='" + getMessagesReadAt() + "'" +
            ", reactionsReadAt='" + getReactionsReadAt() + "'" +
            ", lastActivity='" + getLastActivity() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
