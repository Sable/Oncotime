/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.analysis;

import java.util.*;
import otc.node.*;

public class DepthFirstAdapter extends AnalysisAdapter
{
    public void inStart(Start node)
    {
        defaultIn(node);
    }

    public void outStart(Start node)
    {
        defaultOut(node);
    }

    public void defaultIn(@SuppressWarnings("unused") Node node)
    {
        // Do nothing
    }

    public void defaultOut(@SuppressWarnings("unused") Node node)
    {
        // Do nothing
    }

    @Override
    public void caseStart(Start node)
    {
        inStart(node);
        node.getPProgram().apply(this);
        node.getEOF().apply(this);
        outStart(node);
    }

    public void inAOncoprogramProgram(AOncoprogramProgram node)
    {
        defaultIn(node);
    }

    public void outAOncoprogramProgram(AOncoprogramProgram node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAOncoprogramProgram(AOncoprogramProgram node)
    {
        inAOncoprogramProgram(node);
        if(node.getHeader() != null)
        {
            node.getHeader().apply(this);
        }
        {
            List<PGroupDefinitions> copy = new ArrayList<PGroupDefinitions>(node.getGroupDefinitions());
            for(PGroupDefinitions e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PFilterDefinitions> copy = new ArrayList<PFilterDefinitions>(node.getFilterDefinitions());
            for(PFilterDefinitions e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getComputationList() != null)
        {
            node.getComputationList().apply(this);
        }
        outAOncoprogramProgram(node);
    }

    public void inAGroupfileProgram(AGroupfileProgram node)
    {
        defaultIn(node);
    }

    public void outAGroupfileProgram(AGroupfileProgram node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAGroupfileProgram(AGroupfileProgram node)
    {
        inAGroupfileProgram(node);
        if(node.getTDocComment() != null)
        {
            node.getTDocComment().apply(this);
        }
        {
            List<PGroupDefinitions> copy = new ArrayList<PGroupDefinitions>(node.getGroupDefinitions());
            for(PGroupDefinitions e : copy)
            {
                e.apply(this);
            }
        }
        outAGroupfileProgram(node);
    }

    public void inAHeader(AHeader node)
    {
        defaultIn(node);
    }

    public void outAHeader(AHeader node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAHeader(AHeader node)
    {
        inAHeader(node);
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        {
            List<PTypedName> copy = new ArrayList<PTypedName>(node.getParameters());
            for(PTypedName e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getScriptComment() != null)
        {
            node.getScriptComment().apply(this);
        }
        {
            List<PDependencies> copy = new ArrayList<PDependencies>(node.getUses());
            for(PDependencies e : copy)
            {
                e.apply(this);
            }
        }
        outAHeader(node);
    }

    public void inADependencies(ADependencies node)
    {
        defaultIn(node);
    }

    public void outADependencies(ADependencies node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADependencies(ADependencies node)
    {
        inADependencies(node);
        {
            List<TTGroupFile> copy = new ArrayList<TTGroupFile>(node.getTGroupFile());
            for(TTGroupFile e : copy)
            {
                e.apply(this);
            }
        }
        outADependencies(node);
    }

    public void inAGroupDefinitions(AGroupDefinitions node)
    {
        defaultIn(node);
    }

    public void outAGroupDefinitions(AGroupDefinitions node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAGroupDefinitions(AGroupDefinitions node)
    {
        inAGroupDefinitions(node);
        if(node.getTypedName() != null)
        {
            node.getTypedName().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outAGroupDefinitions(node);
    }

    public void inAPopulationFilterFilterDefinitions(APopulationFilterFilterDefinitions node)
    {
        defaultIn(node);
    }

    public void outAPopulationFilterFilterDefinitions(APopulationFilterFilterDefinitions node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAPopulationFilterFilterDefinitions(APopulationFilterFilterDefinitions node)
    {
        inAPopulationFilterFilterDefinitions(node);
        {
            List<PFilterList> copy = new ArrayList<PFilterList>(node.getFilterList());
            for(PFilterList e : copy)
            {
                e.apply(this);
            }
        }
        outAPopulationFilterFilterDefinitions(node);
    }

    public void inAPeriodFilterFilterDefinitions(APeriodFilterFilterDefinitions node)
    {
        defaultIn(node);
    }

    public void outAPeriodFilterFilterDefinitions(APeriodFilterFilterDefinitions node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAPeriodFilterFilterDefinitions(APeriodFilterFilterDefinitions node)
    {
        inAPeriodFilterFilterDefinitions(node);
        {
            List<PFilterList> copy = new ArrayList<PFilterList>(node.getFilterList());
            for(PFilterList e : copy)
            {
                e.apply(this);
            }
        }
        outAPeriodFilterFilterDefinitions(node);
    }

    public void inAEventFilterFilterDefinitions(AEventFilterFilterDefinitions node)
    {
        defaultIn(node);
    }

    public void outAEventFilterFilterDefinitions(AEventFilterFilterDefinitions node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAEventFilterFilterDefinitions(AEventFilterFilterDefinitions node)
    {
        inAEventFilterFilterDefinitions(node);
        {
            List<PFilterList> copy = new ArrayList<PFilterList>(node.getFilterList());
            for(PFilterList e : copy)
            {
                e.apply(this);
            }
        }
        outAEventFilterFilterDefinitions(node);
    }

    public void inADoctorFilterFilterDefinitions(ADoctorFilterFilterDefinitions node)
    {
        defaultIn(node);
    }

    public void outADoctorFilterFilterDefinitions(ADoctorFilterFilterDefinitions node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADoctorFilterFilterDefinitions(ADoctorFilterFilterDefinitions node)
    {
        inADoctorFilterFilterDefinitions(node);
        {
            List<PFilterList> copy = new ArrayList<PFilterList>(node.getFilterList());
            for(PFilterList e : copy)
            {
                e.apply(this);
            }
        }
        outADoctorFilterFilterDefinitions(node);
    }

    public void inAFilterList(AFilterList node)
    {
        defaultIn(node);
    }

    public void outAFilterList(AFilterList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFilterList(AFilterList node)
    {
        inAFilterList(node);
        if(node.getType() != null)
        {
            node.getType().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outAFilterList(node);
    }

    public void inAComputationList(AComputationList node)
    {
        defaultIn(node);
    }

    public void outAComputationList(AComputationList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAComputationList(AComputationList node)
    {
        inAComputationList(node);
        {
            List<PComputation> copy = new ArrayList<PComputation>(node.getComputation());
            for(PComputation e : copy)
            {
                e.apply(this);
            }
        }
        outAComputationList(node);
    }

    public void inAComputation(AComputation node)
    {
        defaultIn(node);
    }

    public void outAComputation(AComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAComputation(AComputation node)
    {
        inAComputation(node);
        outAComputation(node);
    }

    public void inAForeachComputation(AForeachComputation node)
    {
        defaultIn(node);
    }

    public void outAForeachComputation(AForeachComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAForeachComputation(AForeachComputation node)
    {
        inAForeachComputation(node);
        if(node.getActor() != null)
        {
            node.getActor().apply(this);
        }
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        if(node.getComputation() != null)
        {
            node.getComputation().apply(this);
        }
        outAForeachComputation(node);
    }

    public void inAForeachSetComputation(AForeachSetComputation node)
    {
        defaultIn(node);
    }

    public void outAForeachSetComputation(AForeachSetComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAForeachSetComputation(AForeachSetComputation node)
    {
        inAForeachSetComputation(node);
        if(node.getActor() != null)
        {
            node.getActor().apply(this);
        }
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        if(node.getComputationList() != null)
        {
            node.getComputationList().apply(this);
        }
        outAForeachSetComputation(node);
    }

    public void inAForeachTableComputation(AForeachTableComputation node)
    {
        defaultIn(node);
    }

    public void outAForeachTableComputation(AForeachTableComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAForeachTableComputation(AForeachTableComputation node)
    {
        inAForeachTableComputation(node);
        if(node.getIterator() != null)
        {
            node.getIterator().apply(this);
        }
        if(node.getVariable() != null)
        {
            node.getVariable().apply(this);
        }
        if(node.getComputation() != null)
        {
            node.getComputation().apply(this);
        }
        outAForeachTableComputation(node);
    }

    public void inAForeachTableSetComputation(AForeachTableSetComputation node)
    {
        defaultIn(node);
    }

    public void outAForeachTableSetComputation(AForeachTableSetComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAForeachTableSetComputation(AForeachTableSetComputation node)
    {
        inAForeachTableSetComputation(node);
        if(node.getIterator() != null)
        {
            node.getIterator().apply(this);
        }
        if(node.getVariable() != null)
        {
            node.getVariable().apply(this);
        }
        if(node.getComputationList() != null)
        {
            node.getComputationList().apply(this);
        }
        outAForeachTableSetComputation(node);
    }

    public void inAForeachSequenceComputation(AForeachSequenceComputation node)
    {
        defaultIn(node);
    }

    public void outAForeachSequenceComputation(AForeachSequenceComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAForeachSequenceComputation(AForeachSequenceComputation node)
    {
        inAForeachSequenceComputation(node);
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        if(node.getSequence() != null)
        {
            node.getSequence().apply(this);
        }
        if(node.getComputation() != null)
        {
            node.getComputation().apply(this);
        }
        outAForeachSequenceComputation(node);
    }

    public void inAForeachSequenceSetComputation(AForeachSequenceSetComputation node)
    {
        defaultIn(node);
    }

    public void outAForeachSequenceSetComputation(AForeachSequenceSetComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAForeachSequenceSetComputation(AForeachSequenceSetComputation node)
    {
        inAForeachSequenceSetComputation(node);
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        if(node.getSequence() != null)
        {
            node.getSequence().apply(this);
        }
        if(node.getComputationList() != null)
        {
            node.getComputationList().apply(this);
        }
        outAForeachSequenceSetComputation(node);
    }

    public void inAForeachMemberComputation(AForeachMemberComputation node)
    {
        defaultIn(node);
    }

    public void outAForeachMemberComputation(AForeachMemberComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAForeachMemberComputation(AForeachMemberComputation node)
    {
        inAForeachMemberComputation(node);
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        if(node.getSequencename() != null)
        {
            node.getSequencename().apply(this);
        }
        if(node.getComputation() != null)
        {
            node.getComputation().apply(this);
        }
        outAForeachMemberComputation(node);
    }

    public void inAForeachMemberSetComputation(AForeachMemberSetComputation node)
    {
        defaultIn(node);
    }

    public void outAForeachMemberSetComputation(AForeachMemberSetComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAForeachMemberSetComputation(AForeachMemberSetComputation node)
    {
        inAForeachMemberSetComputation(node);
        if(node.getName() != null)
        {
            node.getName().apply(this);
        }
        if(node.getSequencename() != null)
        {
            node.getSequencename().apply(this);
        }
        if(node.getComputationList() != null)
        {
            node.getComputationList().apply(this);
        }
        outAForeachMemberSetComputation(node);
    }

    public void inAPrintComputation(APrintComputation node)
    {
        defaultIn(node);
    }

    public void outAPrintComputation(APrintComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAPrintComputation(APrintComputation node)
    {
        inAPrintComputation(node);
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        outAPrintComputation(node);
    }

    public void inAPrintAttrComputation(APrintAttrComputation node)
    {
        defaultIn(node);
    }

    public void outAPrintAttrComputation(APrintAttrComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAPrintAttrComputation(APrintAttrComputation node)
    {
        inAPrintAttrComputation(node);
        {
            List<PType> copy = new ArrayList<PType>(node.getAttrList());
            for(PType e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        outAPrintAttrComputation(node);
    }

    public void inAPrintTimelineComputation(APrintTimelineComputation node)
    {
        defaultIn(node);
    }

    public void outAPrintTimelineComputation(APrintTimelineComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAPrintTimelineComputation(APrintTimelineComputation node)
    {
        inAPrintTimelineComputation(node);
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        outAPrintTimelineComputation(node);
    }

    public void inAPrintLengthComputation(APrintLengthComputation node)
    {
        defaultIn(node);
    }

    public void outAPrintLengthComputation(APrintLengthComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAPrintLengthComputation(APrintLengthComputation node)
    {
        inAPrintLengthComputation(node);
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        outAPrintLengthComputation(node);
    }

    public void inAPrintTableitemComputation(APrintTableitemComputation node)
    {
        defaultIn(node);
    }

    public void outAPrintTableitemComputation(APrintTableitemComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAPrintTableitemComputation(APrintTableitemComputation node)
    {
        inAPrintTableitemComputation(node);
        if(node.getVariable() != null)
        {
            node.getVariable().apply(this);
        }
        if(node.getIterator() != null)
        {
            node.getIterator().apply(this);
        }
        outAPrintTableitemComputation(node);
    }

    public void inATableComputation(ATableComputation node)
    {
        defaultIn(node);
    }

    public void outATableComputation(ATableComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseATableComputation(ATableComputation node)
    {
        inATableComputation(node);
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        if(node.getActor() != null)
        {
            node.getActor().apply(this);
        }
        if(node.getType() != null)
        {
            node.getType().apply(this);
        }
        outATableComputation(node);
    }

    public void inABarchartComputation(ABarchartComputation node)
    {
        defaultIn(node);
    }

    public void outABarchartComputation(ABarchartComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseABarchartComputation(ABarchartComputation node)
    {
        inABarchartComputation(node);
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        outABarchartComputation(node);
    }

    public void inAListComputation(AListComputation node)
    {
        defaultIn(node);
    }

    public void outAListComputation(AListComputation node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAListComputation(AListComputation node)
    {
        inAListComputation(node);
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        if(node.getSequence() != null)
        {
            node.getSequence().apply(this);
        }
        outAListComputation(node);
    }

    public void inAAttrList(AAttrList node)
    {
        defaultIn(node);
    }

    public void outAAttrList(AAttrList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAAttrList(AAttrList node)
    {
        inAAttrList(node);
        {
            List<PType> copy = new ArrayList<PType>(node.getType());
            for(PType e : copy)
            {
                e.apply(this);
            }
        }
        outAAttrList(node);
    }

    public void inASequence(ASequence node)
    {
        defaultIn(node);
    }

    public void outASequence(ASequence node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASequence(ASequence node)
    {
        inASequence(node);
        {
            List<PSequenceItem> copy = new ArrayList<PSequenceItem>(node.getSequenceItem());
            for(PSequenceItem e : copy)
            {
                e.apply(this);
            }
        }
        outASequence(node);
    }

    public void inASequenceItem(ASequenceItem node)
    {
        defaultIn(node);
    }

    public void outASequenceItem(ASequenceItem node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASequenceItem(ASequenceItem node)
    {
        inASequenceItem(node);
        outASequenceItem(node);
    }

    public void inANotSequenceItem(ANotSequenceItem node)
    {
        defaultIn(node);
    }

    public void outANotSequenceItem(ANotSequenceItem node)
    {
        defaultOut(node);
    }

    @Override
    public void caseANotSequenceItem(ANotSequenceItem node)
    {
        inANotSequenceItem(node);
        if(node.getEventItem() != null)
        {
            node.getEventItem().apply(this);
        }
        outANotSequenceItem(node);
    }

    public void inADisjunctionSequenceItem(ADisjunctionSequenceItem node)
    {
        defaultIn(node);
    }

    public void outADisjunctionSequenceItem(ADisjunctionSequenceItem node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADisjunctionSequenceItem(ADisjunctionSequenceItem node)
    {
        inADisjunctionSequenceItem(node);
        {
            List<PEventItem> copy = new ArrayList<PEventItem>(node.getEventItem());
            for(PEventItem e : copy)
            {
                e.apply(this);
            }
        }
        outADisjunctionSequenceItem(node);
    }

    public void inAPermutationSequenceItem(APermutationSequenceItem node)
    {
        defaultIn(node);
    }

    public void outAPermutationSequenceItem(APermutationSequenceItem node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAPermutationSequenceItem(APermutationSequenceItem node)
    {
        inAPermutationSequenceItem(node);
        {
            List<PEventItem> copy = new ArrayList<PEventItem>(node.getEventItem());
            for(PEventItem e : copy)
            {
                e.apply(this);
            }
        }
        outAPermutationSequenceItem(node);
    }

    public void inANoparamEventItem(ANoparamEventItem node)
    {
        defaultIn(node);
    }

    public void outANoparamEventItem(ANoparamEventItem node)
    {
        defaultOut(node);
    }

    @Override
    public void caseANoparamEventItem(ANoparamEventItem node)
    {
        inANoparamEventItem(node);
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        outANoparamEventItem(node);
    }

    public void inAParamEventItem(AParamEventItem node)
    {
        defaultIn(node);
    }

    public void outAParamEventItem(AParamEventItem node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAParamEventItem(AParamEventItem node)
    {
        inAParamEventItem(node);
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        {
            List<TTIdentifier> copy = new ArrayList<TTIdentifier>(node.getEventParamList());
            for(TTIdentifier e : copy)
            {
                e.apply(this);
            }
        }
        outAParamEventItem(node);
    }

    public void inAIdType(AIdType node)
    {
        defaultIn(node);
    }

    public void outAIdType(AIdType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAIdType(AIdType node)
    {
        inAIdType(node);
        if(node.getTIdType() != null)
        {
            node.getTIdType().apply(this);
        }
        outAIdType(node);
    }

    public void inASexType(ASexType node)
    {
        defaultIn(node);
    }

    public void outASexType(ASexType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASexType(ASexType node)
    {
        inASexType(node);
        if(node.getTSexType() != null)
        {
            node.getTSexType().apply(this);
        }
        outASexType(node);
    }

    public void inABirthyearType(ABirthyearType node)
    {
        defaultIn(node);
    }

    public void outABirthyearType(ABirthyearType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseABirthyearType(ABirthyearType node)
    {
        inABirthyearType(node);
        if(node.getTBirthyearType() != null)
        {
            node.getTBirthyearType().apply(this);
        }
        outABirthyearType(node);
    }

    public void inADiagnosisType(ADiagnosisType node)
    {
        defaultIn(node);
    }

    public void outADiagnosisType(ADiagnosisType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADiagnosisType(ADiagnosisType node)
    {
        inADiagnosisType(node);
        if(node.getTDiagnosisType() != null)
        {
            node.getTDiagnosisType().apply(this);
        }
        outADiagnosisType(node);
    }

    public void inAPostalcodeType(APostalcodeType node)
    {
        defaultIn(node);
    }

    public void outAPostalcodeType(APostalcodeType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAPostalcodeType(APostalcodeType node)
    {
        inAPostalcodeType(node);
        if(node.getTPostalcodeType() != null)
        {
            node.getTPostalcodeType().apply(this);
        }
        outAPostalcodeType(node);
    }

    public void inAYearsType(AYearsType node)
    {
        defaultIn(node);
    }

    public void outAYearsType(AYearsType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAYearsType(AYearsType node)
    {
        inAYearsType(node);
        if(node.getTYearsType() != null)
        {
            node.getTYearsType().apply(this);
        }
        outAYearsType(node);
    }

    public void inAMonthsType(AMonthsType node)
    {
        defaultIn(node);
    }

    public void outAMonthsType(AMonthsType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMonthsType(AMonthsType node)
    {
        inAMonthsType(node);
        if(node.getTMonthsType() != null)
        {
            node.getTMonthsType().apply(this);
        }
        outAMonthsType(node);
    }

    public void inADaysType(ADaysType node)
    {
        defaultIn(node);
    }

    public void outADaysType(ADaysType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADaysType(ADaysType node)
    {
        inADaysType(node);
        if(node.getTDaysType() != null)
        {
            node.getTDaysType().apply(this);
        }
        outADaysType(node);
    }

    public void inADateType(ADateType node)
    {
        defaultIn(node);
    }

    public void outADateType(ADateType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADateType(ADateType node)
    {
        inADateType(node);
        if(node.getTDateType() != null)
        {
            node.getTDateType().apply(this);
        }
        outADateType(node);
    }

    public void inAEventType(AEventType node)
    {
        defaultIn(node);
    }

    public void outAEventType(AEventType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAEventType(AEventType node)
    {
        inAEventType(node);
        if(node.getTEventType() != null)
        {
            node.getTEventType().apply(this);
        }
        outAEventType(node);
    }

    public void inAHourType(AHourType node)
    {
        defaultIn(node);
    }

    public void outAHourType(AHourType node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAHourType(AHourType node)
    {
        inAHourType(node);
        if(node.getTHoursType() != null)
        {
            node.getTHoursType().apply(this);
        }
        outAHourType(node);
    }

    public void inANumberListItemTypedList(ANumberListItemTypedList node)
    {
        defaultIn(node);
    }

    public void outANumberListItemTypedList(ANumberListItemTypedList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseANumberListItemTypedList(ANumberListItemTypedList node)
    {
        inANumberListItemTypedList(node);
        if(node.getTNumber() != null)
        {
            node.getTNumber().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outANumberListItemTypedList(node);
    }

    public void inAMaleListItemTypedList(AMaleListItemTypedList node)
    {
        defaultIn(node);
    }

    public void outAMaleListItemTypedList(AMaleListItemTypedList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMaleListItemTypedList(AMaleListItemTypedList node)
    {
        inAMaleListItemTypedList(node);
        if(node.getTMale() != null)
        {
            node.getTMale().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outAMaleListItemTypedList(node);
    }

    public void inAFemaleListItemTypedList(AFemaleListItemTypedList node)
    {
        defaultIn(node);
    }

    public void outAFemaleListItemTypedList(AFemaleListItemTypedList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFemaleListItemTypedList(AFemaleListItemTypedList node)
    {
        inAFemaleListItemTypedList(node);
        if(node.getTFemale() != null)
        {
            node.getTFemale().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outAFemaleListItemTypedList(node);
    }

    public void inAPostalcodeListItemTypedList(APostalcodeListItemTypedList node)
    {
        defaultIn(node);
    }

    public void outAPostalcodeListItemTypedList(APostalcodeListItemTypedList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAPostalcodeListItemTypedList(APostalcodeListItemTypedList node)
    {
        inAPostalcodeListItemTypedList(node);
        if(node.getTPostalcode() != null)
        {
            node.getTPostalcode().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outAPostalcodeListItemTypedList(node);
    }

    public void inAYearListItemTypedList(AYearListItemTypedList node)
    {
        defaultIn(node);
    }

    public void outAYearListItemTypedList(AYearListItemTypedList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAYearListItemTypedList(AYearListItemTypedList node)
    {
        inAYearListItemTypedList(node);
        if(node.getTYear() != null)
        {
            node.getTYear().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outAYearListItemTypedList(node);
    }

    public void inAMonthsListItemTypedList(AMonthsListItemTypedList node)
    {
        defaultIn(node);
    }

    public void outAMonthsListItemTypedList(AMonthsListItemTypedList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMonthsListItemTypedList(AMonthsListItemTypedList node)
    {
        inAMonthsListItemTypedList(node);
        if(node.getTMonth() != null)
        {
            node.getTMonth().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outAMonthsListItemTypedList(node);
    }

    public void inADaysListItemTypedList(ADaysListItemTypedList node)
    {
        defaultIn(node);
    }

    public void outADaysListItemTypedList(ADaysListItemTypedList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADaysListItemTypedList(ADaysListItemTypedList node)
    {
        inADaysListItemTypedList(node);
        if(node.getTDay() != null)
        {
            node.getTDay().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outADaysListItemTypedList(node);
    }

    public void inADateListItemTypedList(ADateListItemTypedList node)
    {
        defaultIn(node);
    }

    public void outADateListItemTypedList(ADateListItemTypedList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADateListItemTypedList(ADateListItemTypedList node)
    {
        inADateListItemTypedList(node);
        if(node.getTDate() != null)
        {
            node.getTDate().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outADateListItemTypedList(node);
    }

    public void inAStringListItemTypedList(AStringListItemTypedList node)
    {
        defaultIn(node);
    }

    public void outAStringListItemTypedList(AStringListItemTypedList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAStringListItemTypedList(AStringListItemTypedList node)
    {
        inAStringListItemTypedList(node);
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outAStringListItemTypedList(node);
    }

    public void inAHourListItemTypedList(AHourListItemTypedList node)
    {
        defaultIn(node);
    }

    public void outAHourListItemTypedList(AHourListItemTypedList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAHourListItemTypedList(AHourListItemTypedList node)
    {
        inAHourListItemTypedList(node);
        if(node.getTHour() != null)
        {
            node.getTHour().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outAHourListItemTypedList(node);
    }

    public void inAExpandableListItemTypedList(AExpandableListItemTypedList node)
    {
        defaultIn(node);
    }

    public void outAExpandableListItemTypedList(AExpandableListItemTypedList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAExpandableListItemTypedList(AExpandableListItemTypedList node)
    {
        inAExpandableListItemTypedList(node);
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outAExpandableListItemTypedList(node);
    }

    public void inAAllListItemTypedList(AAllListItemTypedList node)
    {
        defaultIn(node);
    }

    public void outAAllListItemTypedList(AAllListItemTypedList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAAllListItemTypedList(AAllListItemTypedList node)
    {
        inAAllListItemTypedList(node);
        if(node.getTStar() != null)
        {
            node.getTStar().apply(this);
        }
        if(node.getTypedList() != null)
        {
            node.getTypedList().apply(this);
        }
        outAAllListItemTypedList(node);
    }

    public void inATypedName(ATypedName node)
    {
        defaultIn(node);
    }

    public void outATypedName(ATypedName node)
    {
        defaultOut(node);
    }

    @Override
    public void caseATypedName(ATypedName node)
    {
        inATypedName(node);
        if(node.getType() != null)
        {
            node.getType().apply(this);
        }
        if(node.getTIdentifier() != null)
        {
            node.getTIdentifier().apply(this);
        }
        outATypedName(node);
    }

    public void inAPatientActor(APatientActor node)
    {
        defaultIn(node);
    }

    public void outAPatientActor(APatientActor node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAPatientActor(APatientActor node)
    {
        inAPatientActor(node);
        if(node.getTPatientType() != null)
        {
            node.getTPatientType().apply(this);
        }
        outAPatientActor(node);
    }

    public void inADoctorActor(ADoctorActor node)
    {
        defaultIn(node);
    }

    public void outADoctorActor(ADoctorActor node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADoctorActor(ADoctorActor node)
    {
        inADoctorActor(node);
        if(node.getTDoctorType() != null)
        {
            node.getTDoctorType().apply(this);
        }
        outADoctorActor(node);
    }

    public void inADiagnosisActor(ADiagnosisActor node)
    {
        defaultIn(node);
    }

    public void outADiagnosisActor(ADiagnosisActor node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADiagnosisActor(ADiagnosisActor node)
    {
        inADiagnosisActor(node);
        if(node.getTDiagnosisType() != null)
        {
            node.getTDiagnosisType().apply(this);
        }
        outADiagnosisActor(node);
    }
}