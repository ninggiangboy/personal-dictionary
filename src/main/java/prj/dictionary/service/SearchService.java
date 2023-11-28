package prj.dictionary.service;

import prj.dictionary.entity.Word;

import java.util.List;

public interface SearchService {
    List<Word> search(String keyword, List<Word> words, int mode);
}
