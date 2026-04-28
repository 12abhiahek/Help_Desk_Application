package com.helpdesk.HelpDeskWebApplication.repository;

import com.helpdesk.HelpDeskWebApplication.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

   Optional<Ticket> findById(Long ticketId);
    Optional<Ticket> findByEmail(String email);

}
