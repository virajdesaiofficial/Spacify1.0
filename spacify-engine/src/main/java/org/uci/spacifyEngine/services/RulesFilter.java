package org.uci.spacifyEngine.services;

import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import java.util.List;

public class RulesFilter implements AgendaFilter {

    private List<String> rulesToMatch;

    public List<String> getRulesToMatch() {
        return rulesToMatch;
    }

    public RulesFilter(List<String> rulesToFilter){
       this.rulesToMatch = rulesToFilter;
    }

    @Override
    public boolean accept(Match match) {
        return getRulesToMatch().contains(match.getRule().getName());
    }


}
