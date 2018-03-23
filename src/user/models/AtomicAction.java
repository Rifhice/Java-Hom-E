package user.models;

/**
 * An action is a message sent by the server to the actuators which needs to react according to it.
 * @author Clm-Roig
 */
public class AtomicAction {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String name;
    private String executable;
    
    // Attributes from other tables

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public AtomicAction() {}

    public AtomicAction(int id, String name, String executable) {
        this.id = id;
        this.name = name;
        this.executable = executable;
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExecutable() {
        return executable;
    }

    public void setExecutable(String executable) {
        this.executable = executable;
    }
    
    // ================================
    public String toString() {
        String res = "AT.ACTION #"+id+" "+name ;
        res += "  " + executable;      
        return res;
    }
    
    public void execute() {
        // TODO
    }
    
    

    

}
