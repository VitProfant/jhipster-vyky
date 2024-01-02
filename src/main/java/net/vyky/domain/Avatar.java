package net.vyky.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Avatar.
 */
@Entity
@Table(name = "avatar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Avatar implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "mime", nullable = false)
    private String mime;

    @Lob
    @Column(name = "img", nullable = false)
    private byte[] img;

    @NotNull
    @Column(name = "img_content_type", nullable = false)
    private String imgContentType;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "activation_at")
    private ZonedDateTime activationAt;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private VykyUser vykyUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Avatar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMime() {
        return this.mime;
    }

    public Avatar mime(String mime) {
        this.setMime(mime);
        return this;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public byte[] getImg() {
        return this.img;
    }

    public Avatar img(byte[] img) {
        this.setImg(img);
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return this.imgContentType;
    }

    public Avatar imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Avatar active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ZonedDateTime getActivationAt() {
        return this.activationAt;
    }

    public Avatar activationAt(ZonedDateTime activationAt) {
        this.setActivationAt(activationAt);
        return this;
    }

    public void setActivationAt(ZonedDateTime activationAt) {
        this.activationAt = activationAt;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Avatar createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getDeletedAt() {
        return this.deletedAt;
    }

    public Avatar deletedAt(ZonedDateTime deletedAt) {
        this.setDeletedAt(deletedAt);
        return this;
    }

    public void setDeletedAt(ZonedDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public VykyUser getVykyUser() {
        return this.vykyUser;
    }

    public void setVykyUser(VykyUser vykyUser) {
        this.vykyUser = vykyUser;
    }

    public Avatar vykyUser(VykyUser vykyUser) {
        this.setVykyUser(vykyUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avatar)) {
            return false;
        }
        return getId() != null && getId().equals(((Avatar) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Avatar{" +
            "id=" + getId() +
            ", mime='" + getMime() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            ", active='" + getActive() + "'" +
            ", activationAt='" + getActivationAt() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
