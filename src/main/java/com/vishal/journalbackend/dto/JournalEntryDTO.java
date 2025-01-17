package com.vishal.journalbackend.dto;

import com.vishal.journalbackend.enums.Sentiment;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntryDTO {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private Sentiment sentiment;

}