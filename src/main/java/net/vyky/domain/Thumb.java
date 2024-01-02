package net.vyky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Thumb.
 */
@Entity
@Table(name = "thumb")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Thumb implements Serializable {

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
    @Column(name = "up", nullable = false)
    private Boolean up;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "topic", "parent", "author" }, allowSetters = true)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private VykyUser vykyUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Thumb id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public Thumb version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getUp() {
        return this.up;
    }

    public Thumb up(Boolean up) {
        this.setUp(up);
        return this;
    }

    public void setUp(Boolean up) {
        this.up = up;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Thumb createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public Thumb updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ZonedDateTime getDeletedAt() {
        return this.deletedAt;
    }

    public Thumb deletedAt(ZonedDateTime deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(ZonedDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Post getPost() {
        return this.post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Thumb post(Post post) {
        this.setPost(post);
        return this;
    }

    public VykyUser getVykyUser() {
        return this.vykyUser;
    }

    public void setVykyUser(VykyUser vykyUser) {
        this.vykyUser = vykyUser;
    }

    public Thumb vykyUser(VykyUser vykyUser) {
        this.setVykyUser(vykyUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thumb)) {
            return false;
        }
        return getId() != null && getId().equals(((Thumb) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thumb{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", up='" + getUp() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
