package com.abhi.messweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.abhi.messweb.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}