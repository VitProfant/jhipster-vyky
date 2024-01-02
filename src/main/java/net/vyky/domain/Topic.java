package net.vyky.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import net.vyky.domain.enumeration.PostStatus;
import net.vyky.domain.enumeration.Role;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Topic.
 */
@Entity
@Table(name = "topic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Topic implements Serializable {

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
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "minimal_role")
    private Role minimalRole;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostStatus status;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Area area;

    @ManyToOne(fetch = FetchType.LAZY)
    private VykyUser vykyUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Topic id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public Topic version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return this.name;
    }

    public Topic name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Topic description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Role getMinimalRole() {
        return this.minimalRole;
    }

    public Topic minimalRole(Role minimalRole) {
        this.setMinimalRole(minimalRole);
        return this;
    }

    public void setMinimalRole(Role minimalRole) {
        this.minimalRole = minimalRole;
    }

    public PostStatus getStatus() {
        return this.status;
    }

    public Topic status(PostStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Topic createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public Topic updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ZonedDateTime getDeletedAt() {
        return this.deletedAt;
    }

    public Topic deletedAt(ZonedDateTime deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(ZonedDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Area getArea() {
        return this.area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Topic area(Area area) {
        this.setArea(area);
        return this;
    }

    public VykyUser getVykyUser() {
        return this.vykyUser;
    }

    public void setVykyUser(VykyUser vykyUser) {
        this.vykyUser = vykyUser;
    }

    public Topic vykyUser(VykyUser vykyUser) {
        this.setVykyUser(vykyUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Topic)) {
            return false;
        }
        return getId() != null && getId().equals(((Topic) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Topic{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", minimalRole='" + getMinimalRole() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
