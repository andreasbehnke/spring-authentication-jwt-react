package org.buildingblock.springauthjwt.service.repositories;

import org.buildingblock.springauthjwt.entities.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserTicketRepository extends JpaRepository<UserTicket, UUID> {

}
