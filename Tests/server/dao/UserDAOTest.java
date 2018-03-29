package server.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.management.relation.Role;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import server.dao.abstractDAO.RoleDAO;
import server.dao.abstractDAO.UserDAO;
import server.factories.AbstractDAOFactory;
import server.managers.SystemManager;
import server.models.User;
import server.tools.SQLiteDatabaseManager;

class UserDAOTest {

    private UserDAO userDAO = AbstractDAOFactory.getFactory(SystemManager.db).getUserDAO();
    private RoleDAO roleDAO = AbstractDAOFactory.getFactory(SystemManager.db).getRoleDAO();

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        SQLiteDatabaseManager.main(null);   
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testGetById() {
        assertTrue(userDAO.getById(1).getId() == 1);
        assertTrue(userDAO.getById(2137534) == null);
    }

    @Test
    void testCreateT() {
        User user = new User();
        user.setId(42);
        user.setPseudo("test TEST");
        user.setPassword("MyPassw0rd");
        
        server.models.Role role = roleDAO.getByName("owner");
        user.setRole(role);
        
        User userCreated = userDAO.create(user);
        assertTrue(userCreated.getId() == user.getId());
        assertTrue(userCreated.getPseudo() == user.getPseudo());
        assertTrue(userCreated.getPassword() == user.getPassword());
        assertTrue(userCreated.getRole() == user.getRole());

        
    }

}
