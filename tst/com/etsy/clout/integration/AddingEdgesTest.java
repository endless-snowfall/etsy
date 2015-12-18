package com.etsy.clout.integration;

import static com.etsy.clout.common.TestUtils.*;
import static com.etsy.clout.common.TestUtils.People.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.etsy.clout.concepts.Person;
import com.etsy.clout.service.CloutService;
import com.etsy.clout.service.CloutServiceImpl;

public class AddingEdgesTest {

    private CloutService cloutService;

    @Before
    public void before() {
        cloutService = new CloutServiceImpl();
    }

    @Test
    public void addEdge_TreeComponent_To_TreeComponent() {
        Map<Person, Integer> anthonyTreeClout = createAnthonyRootedTreeComponent(cloutService);
        Map<Person, Integer> daveTreeClout = createDaveRootedTreeComponent(cloutService);

        cloutService.follows(DAVE, MIKE);

        int increase = cloutService.getClout(DAVE) + 1;
        assertIncreased(cloutService, anthonyTreeClout, increase, ANTHONY, MIKE);
        assertUnchanged(cloutService, anthonyTreeClout, BOB);
        assertUnchanged(cloutService, daveTreeClout, DAVE, NEYMAR, XAVI);
    }

    @Test
    public void addEdge_TreeComponent_To_CycleComponent() {
        Map<Person, Integer> anthonyTreeClout = createAnthonyRootedTreeComponent(cloutService);
        Map<Person, Integer> cycleClout = createCycleComponent(cloutService);

        cloutService.follows(ANTHONY, MESSI);

        int increase = cloutService.getClout(ANTHONY) + 1;
        assertIncreased(cloutService, cycleClout, increase, MESSI, JAKE, BILL);
        assertUnchanged(cloutService, anthonyTreeClout, ANTHONY, MIKE, BOB);
    }

    @Test
    public void addEdge_CreatingCycle() {
        Map<Person, Integer> anthonyTreeClout = createAnthonyRootedTreeComponent(cloutService);

        cloutService.follows(ANTHONY, BOB);

        assertIncreased(cloutService, anthonyTreeClout, 2, BOB);
        assertUnchanged(cloutService, anthonyTreeClout, ANTHONY, MIKE);
    }
}
