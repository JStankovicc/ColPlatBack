package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.ContactsList;
import com.ColPlat.Backend.model.enums.ContactsListStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ContactListRepository extends JpaRepository<ContactsList, Long> {
    List<ContactsList> findByCompanyIdAndStatus(Long companyId, ContactsListStatus status);

    Collection<ContactsList> findByTeamId(Integer id);
}
