package com.etsy.clout.command;

import static com.etsy.clout.common.TestUtils.People.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.etsy.clout.concepts.Response;
import com.etsy.clout.service.CloutService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class CloutCommandTest {

    @Mock private CloutService cloutService;

    private CloutCommand command;

    @After
    public void after() {
        verifyNoMoreInteractions(cloutService);
    }

    @Test
    public void unknownPerson() {
        when(cloutService.getClout(ANTHONY)).thenReturn(0);
        command = CloutCommand.of(cloutService, Optional.of(ANTHONY));

        assertEquals(new Response("Anthony has no followers."), command.execute());
        verify(cloutService).getClout(ANTHONY);
    }

    @Test
    public void knownPerson_OneClout() {
        when(cloutService.getClout(ANTHONY)).thenReturn(1);
        command = CloutCommand.of(cloutService, Optional.of(ANTHONY));

        assertEquals(new Response("Anthony has 1 follower."), command.execute());
        verify(cloutService).getClout(ANTHONY);
    }

    @Test
    public void knownPerson() {
        when(cloutService.getClout(ANTHONY)).thenReturn(123);
        command = CloutCommand.of(cloutService, Optional.of(ANTHONY));

        assertEquals(new Response("Anthony has 123 followers."), command.execute());
        verify(cloutService).getClout(ANTHONY);
    }

    @Test
    public void allClout_SinglePerson() {
        when(cloutService.getAllClout()).thenReturn(Maps.newHashMap(ImmutableMap.of(ANTHONY, 789)));
        command = CloutCommand.of(cloutService, Optional.empty());

        assertEquals(new Response("Anthony has 789 followers."), command.execute());
        verify(cloutService).getAllClout();
    }

    @Test
    public void allClout() {
        when(cloutService.getAllClout()).thenReturn(Maps.newHashMap(
            ImmutableMap.of(ANTHONY, 789, MIKE, 1)));
        command = CloutCommand.of(cloutService, Optional.empty());

        assertEquals(
            Response.of(Sets.newHashSet(
                "Anthony has 789 followers.",
                "Mike has 1 follower.")),
            command.execute());
        verify(cloutService).getAllClout();
    }
}
