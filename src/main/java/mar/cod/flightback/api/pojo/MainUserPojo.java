package mar.cod.flightback.api.pojo;

import jakarta.validation.Valid;
import mar.cod.flightback.entities.User;

public class MainUserPojo {
    private long creatorId;
    @Valid
    private User user;

    public MainUserPojo(long creatorId, User user) {
        this.creatorId = creatorId;
        this.user = user;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
