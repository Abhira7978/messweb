package com.abhi.messweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.abhi.messweb.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByMemberIdAndDate(Long memberId, LocalDate date);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.member.id = :memberId AND MONTH(a.date) = :month AND YEAR(a.date) = :year")
    Long countMonthlyPresent(@Param("memberId") Long memberId,
                             @Param("month") int month,
                             @Param("year") int year);

    @Query("SELECT a FROM Attendance a WHERE a.member.id = :memberId AND MONTH(a.date) = :month AND YEAR(a.date) = :year")
    List<Attendance> findByMemberIdAndMonthAndYear(@Param("memberId") Long memberId,
                                                   @Param("month") int month,
                                                   @Param("year") int year);
}