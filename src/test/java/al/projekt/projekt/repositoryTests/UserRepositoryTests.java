package al.projekt.projekt.repositoryTests;

import al.projekt.projekt.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userRepoIsAutowired() {
        Assert.assertNotNull(userRepository);
    }

}
