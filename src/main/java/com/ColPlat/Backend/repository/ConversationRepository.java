package com.ColPlat.Backend.repository;

// package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Conversation;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("""
      select distinct c from Conversation c
      join c.participants p
      where c.companyId = :companyId and p.userId = :userId
      order by c.lastMessageAt desc nulls last, c.updatedAt desc
    """)
    List<Conversation> findInbox(@Param("companyId") Long companyId, @Param("userId") Long userId);

    @Query("""
      select c from Conversation c
      join c.participants p1
      join c.participants p2
      where c.companyId = :companyId and c.group = false
        and p1.userId = :userA and p2.userId = :userB
    """)
    Optional<Conversation> findDirectBetween(@Param("companyId") Long companyId,
                                             @Param("userA") Long userA,
                                             @Param("userB") Long userB);
}
