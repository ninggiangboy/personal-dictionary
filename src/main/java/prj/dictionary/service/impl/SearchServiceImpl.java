package prj.dictionary.service.impl;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prj.dictionary.constant.IConstant;
import prj.dictionary.entity.Word;
import prj.dictionary.helper.StringHelper;
import prj.dictionary.repository.WordRepository;
import prj.dictionary.service.SearchService;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    WordRepository wordRepository;

    @Override
    public List<Word> search(String keyword, List<Word> words, int mode) {
        if (keyword.matches(IConstant.SPECIAL_CHAR_REGEX)) {
            return wordRepository.findByTermContains(keyword);
        }
        List<BoundExtractedResult<Word>> searchExtractedResult;
        switch (mode) {
            case 2:
                searchExtractedResult = FuzzySearch.extractSorted(StringHelper.toSlug(keyword), words,
                        x -> StringHelper.toSlug(x.getStringDefinitions()), 50);
                break;
            case 3:
                searchExtractedResult = FuzzySearch.extractSorted(StringHelper.toSlug(keyword), words,
                        x -> StringHelper.toSlug(x.getStringExamples()), 50);
                break;
            default:
                searchExtractedResult = FuzzySearch.extractSorted(StringHelper.toSlug(keyword), words,
                        x -> StringHelper.toSlug(x.getTerm()), 50);
        }
        List<Word> searched = new ArrayList<>();
        for (BoundExtractedResult<Word> result : searchExtractedResult) {
            searched.add(result.getReferent());
        }
        return searched;
    }
}
