package net.vyky.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import net.vyky.domain.enumeration.ConfigItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Config.
 */
@Entity
@Table(name = "config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "config_item", nullable = false)
    private ConfigItem configItem;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

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

    public Config id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConfigItem getConfigItem() {
        return this.configItem;
    }

    public Config configItem(ConfigItem configItem) {
        this.setConfigItem(configItem);
        return this;
    }

    public void setConfigItem(ConfigItem configItem) {
        this.configItem = configItem;
    }

    public Integer getVersion() {
        return this.version;
    }

    public Config version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getValue() {
        return this.value;
    }

    public Config value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ZonedDateTime getCreatedAt() {
        return this.createdAt;
    }

    public Config createdAt(ZonedDateTime createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public Config updatedAt(ZonedDateTime updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ZonedDateTime getDeletedAt() {
        return this.deletedAt;
    }

    public Config deletedAt(ZonedDateTime deletedAt) {
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
        if (!(o instanceof Config)) {
            return false;
        }
        return getId() != null && getId().equals(((Config) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Config{" +
            "id=" + getId() +
            ", configItem='" + getConfigItem() + "'" +
            ", version=" + getVersion() +
            ", value='" + getValue() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
