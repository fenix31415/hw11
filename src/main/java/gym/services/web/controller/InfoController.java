package gym.services.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import gym.events.IEventDao;
import gym.model.DataReader;
import gym.model.DataWriter;
import gym.model.Membership;
import gym.services.ManagerService;

import java.time.Duration;
import java.util.List;

@Controller
public class InfoController {
    private final DataReader dataReader;
    private final ManagerService managerService;
    private long selectedMembershipId = -1;
    private Exception currentException = null;

    public InfoController(final IEventDao eventDao) {
        final DataWriter dataWriter = new DataWriter(eventDao);
        this.dataReader = new DataReader(eventDao);
        this.managerService = new ManagerService(dataReader, dataWriter);
    }

    private void prepareModelMap(final Model map, final List<Membership> memberships) {
        map.addAttribute("membersList", memberships);
        map.addAttribute("curMembershipId", selectedMembershipId);
        map.addAttribute("currentException", currentException);
    }

    private void execute(Runnable runnable) {
        currentException = null;
        try {
            runnable.run();
        } catch (final Exception e) {
            currentException = e;
        }
    }

    @GetMapping(value = "/index")
    public String index(final Model map) {
        prepareModelMap(map, dataReader.getMemberships());
        return "index";
    }

    @PostMapping(value = "/new-membership")
    public String addMembership(@RequestParam("identifier") final long id,
                                @RequestParam("valid for") final long validity) {
        execute(() -> managerService.newMembership(id, Duration.ofDays(validity)));
        return "redirect:/index";
    }

    @PostMapping(value = "/prolongate-membership")
    public String extendMembership(@RequestParam("duration") final long validity) {
        execute(() -> managerService.extendMembership(selectedMembershipId, Duration.ofDays(validity)));
        return "redirect:/index";
    }

    @PostMapping(value = "/select-membership")
    public String selectMembership(@RequestParam("identifier") final long id) {
        execute(() -> selectedMembershipId = id);
        return "redirect:/index";
    }
}