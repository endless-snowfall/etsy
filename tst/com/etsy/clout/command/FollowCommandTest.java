package com.etsy.clout.command;

import static com.etsy.clout.command.FollowCommand.*;
import static com.etsy.clout.common.TestUtils.People.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.etsy.clout.service.CloutService;

@RunWith(MockitoJUnitRunner.class)
public class FollowCommandTest {

    @Mock private CloutService cloutService;

    private FollowCommand command;

    @After
    public void after() {
        verifyNoMoreInteractions(cloutService);
    }

    @Test
    public void selfFollow() {
        command = FollowCommand.of(cloutService, ANTHONY, ANTHONY);
        assertEquals(SELF_FOLLOW_RESPONSE, command.execute());
        verify(cloutService, never()).follows(ANTHONY, ANTHONY);
    }

    @Test
    public void happyCase() {
        command = FollowCommand.of(cloutService, ANTHONY, MIKE);
        assertEquals(OK_RESPONSE, command.execute());
        verify(cloutService).follows(ANTHONY, MIKE);
    }
}
