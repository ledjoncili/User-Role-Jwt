package al.projekt.projekt.repositoryTests;

import al.projekt.projekt.repository.RoleRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void roleRepoIsAutowired() {
        Assert.assertNotNull(roleRepository);
    }
}
