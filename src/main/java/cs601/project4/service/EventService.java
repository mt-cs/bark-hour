package cs601.project4.service;

import Repository.EventRepository;
import java.util.List;
import model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

  /**
   *
   * @return list of events
   */
  public List<Event> getEvents() {
    return eventRepository.findAll();
  }

}
