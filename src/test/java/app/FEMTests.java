package app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FEMTests {

    private final FEM app = new FEM();

    @Test
    public void instanceofFEM() {
        assertEquals(app.getClass(), FEM.class);
    }
}
