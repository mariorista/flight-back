package mar.cod.flightback.entities;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import mar.cod.flightback.utils.Roles;

@Entity
public class User {
    @Transient
    @Value("${default.initial.flights}")
    private Integer initialFlights;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(length = 20)
    @NotBlank
    private String fname;

    @Column(length = 20)
    @NotBlank
    private String lname;

    @Column(length = 20)
    @NotBlank(message = "usr can not be blank")
    @NotNull
    @NotEmpty
    @Size(min = 4, message = "username should contain more than 4 characters")
    private String usr;

    @NotBlank(message = "psw can not be blank")
    @NotNull
    @Size(min = 4, max = 20, message = "psw must be between 4 and 20 characters")
    private String psw;

    @Email(message = "invalid email address")
    @Column(unique = true)
    private String email;

    @Past(message = "birthdate must bee in the past")
    private LocalDate bdate;

    @Min(0)
    @Max(20)
    private Integer remainingFlights;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Transient
    private String alert;

    // @JsonIgnore
    // @ManyToMany
    // @JoinTable(name = "user_flight", joinColumns = @JoinColumn(name =
    // "flight_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    // private Set<Flight> flights = new HashSet<>();

    // @OneToMany(mappedBy = "user")
    // private Set<UserFlightEx> userFlightSet;

    public User() {
    }

    public User(long id, String fname, String lname, String usr, String psw, String email, LocalDate bdate,
            int remainingFlights,
            Roles role) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.usr = usr;
        this.psw = psw;
        this.bdate = bdate;
        this.remainingFlights = remainingFlights;
        this.role = role;
        this.email = email;
    }

    public User(long id, String fname, String lname, String usr, String psw, String email, LocalDate bdate,
            Roles role) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.usr = usr;
        this.psw = psw;
        this.bdate = bdate;
        this.remainingFlights = initialFlights;
        this.role = role;
        this.email = email;
    }

    public User(String fname, String lname, String usr, String psw, String email, LocalDate bdate,
            Roles role) {
        this.fname = fname;
        this.lname = lname;
        this.usr = usr;
        this.psw = psw;
        this.bdate = bdate;
        this.remainingFlights = initialFlights;
        this.role = role;
        this.email = email;
    }

    public Integer getInitialFlights() {
        return initialFlights;
    }

    public void setInitialFlights(Integer initialFlights) {
        this.initialFlights = initialFlights;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBdate() {
        return bdate;
    }

    public void setBdate(LocalDate bdate) {
        this.bdate = bdate;
    }

    public Integer getRemainingFlights() {
        return remainingFlights;
    }

    public void setRemainingFlights(Integer remainingFlights) {
        this.remainingFlights = remainingFlights;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    @Override
    public String toString() {
        return "User [initialFlights=" + initialFlights + ", id=" + id + ", fname=" + fname + ", lname=" + lname
                + ", usr=" + usr + ", psw=" + psw + ", email=" + email + ", bdate=" + bdate + ", remainingFlights="
                + remainingFlights + ", role=" + role + "]";
    }

}
