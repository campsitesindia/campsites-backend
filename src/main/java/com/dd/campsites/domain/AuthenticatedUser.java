package com.dd.campsites.domain;

import com.dd.campsites.domain.enumeration.AuthProvider;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AuthenticatedUser.
 */
@Entity
@Table(name = "authenticated_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuthenticatedUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private AuthProvider provider;

    @Column(name = "auth_timestamp")
    private Instant authTimestamp;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthenticatedUser id(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public AuthenticatedUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public AuthenticatedUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AuthProvider getProvider() {
        return this.provider;
    }

    public AuthenticatedUser provider(AuthProvider provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public Instant getAuthTimestamp() {
        return this.authTimestamp;
    }

    public AuthenticatedUser authTimestamp(Instant authTimestamp) {
        this.authTimestamp = authTimestamp;
        return this;
    }

    public void setAuthTimestamp(Instant authTimestamp) {
        this.authTimestamp = authTimestamp;
    }

    public User getUser() {
        return this.user;
    }

    public AuthenticatedUser user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthenticatedUser)) {
            return false;
        }
        return id != null && id.equals(((AuthenticatedUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthenticatedUser{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", provider='" + getProvider() + "'" +
            ", authTimestamp='" + getAuthTimestamp() + "'" +
            "}";
    }
}
