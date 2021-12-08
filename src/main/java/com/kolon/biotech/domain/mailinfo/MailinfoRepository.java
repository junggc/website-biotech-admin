package com.kolon.biotech.domain.mailinfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MailinfoRepository  extends JpaRepository<Mailinfo, Integer> {
    Mailinfo findByJob(String job);
}
