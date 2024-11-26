package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, BigInteger> {
}
