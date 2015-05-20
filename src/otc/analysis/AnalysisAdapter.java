/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.analysis;

import java.util.*;
import otc.node.*;

public class AnalysisAdapter implements Analysis
{
    private Hashtable<Node,Object> in;
    private Hashtable<Node,Object> out;

    public Object getIn(Node node)
    {
        if(this.in == null)
        {
            return null;
        }

        return this.in.get(node);
    }

    public void setIn(Node node, Object o)
    {
        if(this.in == null)
        {
            this.in = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.in.put(node, o);
        }
        else
        {
            this.in.remove(node);
        }
    }

    public Object getOut(Node node)
    {
        if(this.out == null)
        {
            return null;
        }

        return this.out.get(node);
    }

    public void setOut(Node node, Object o)
    {
        if(this.out == null)
        {
            this.out = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.out.put(node, o);
        }
        else
        {
            this.out.remove(node);
        }
    }

    public void caseStart(Start node)
    {
        defaultCase(node);
    }

    public void caseAProgram(AProgram node)
    {
        defaultCase(node);
    }

    public void caseAHeader(AHeader node)
    {
        defaultCase(node);
    }

    public void caseADependencies(ADependencies node)
    {
        defaultCase(node);
    }

    public void caseAGroupDefinitions(AGroupDefinitions node)
    {
        defaultCase(node);
    }

    public void caseAPopulationFilterFilterDefinitions(APopulationFilterFilterDefinitions node)
    {
        defaultCase(node);
    }

    public void caseAPeriodFilterFilterDefinitions(APeriodFilterFilterDefinitions node)
    {
        defaultCase(node);
    }

    public void caseAEventFilterFilterDefinitions(AEventFilterFilterDefinitions node)
    {
        defaultCase(node);
    }

    public void caseADoctorFilterFilterDefinitions(ADoctorFilterFilterDefinitions node)
    {
        defaultCase(node);
    }

    public void caseAFilterList(AFilterList node)
    {
        defaultCase(node);
    }

    public void caseAComputationList(AComputationList node)
    {
        defaultCase(node);
    }

    public void caseAComputation(AComputation node)
    {
        defaultCase(node);
    }

    public void caseAForeachComputation(AForeachComputation node)
    {
        defaultCase(node);
    }

    public void caseAForeachSetComputation(AForeachSetComputation node)
    {
        defaultCase(node);
    }

    public void caseAForeachTableComputation(AForeachTableComputation node)
    {
        defaultCase(node);
    }

    public void caseAForeachTableSetComputation(AForeachTableSetComputation node)
    {
        defaultCase(node);
    }

    public void caseAForeachSequenceComputation(AForeachSequenceComputation node)
    {
        defaultCase(node);
    }

    public void caseAForeachSequenceSetComputation(AForeachSequenceSetComputation node)
    {
        defaultCase(node);
    }

    public void caseAPrintComputation(APrintComputation node)
    {
        defaultCase(node);
    }

    public void caseAPrintAttrComputation(APrintAttrComputation node)
    {
        defaultCase(node);
    }

    public void caseAPrintTimelineComputation(APrintTimelineComputation node)
    {
        defaultCase(node);
    }

    public void caseAPrintLengthComputation(APrintLengthComputation node)
    {
        defaultCase(node);
    }

    public void caseAPrintTableitemComputation(APrintTableitemComputation node)
    {
        defaultCase(node);
    }

    public void caseATableComputation(ATableComputation node)
    {
        defaultCase(node);
    }

    public void caseABarchartComputation(ABarchartComputation node)
    {
        defaultCase(node);
    }

    public void caseAListComputation(AListComputation node)
    {
        defaultCase(node);
    }

    public void caseAAttrList(AAttrList node)
    {
        defaultCase(node);
    }

    public void caseASequence(ASequence node)
    {
        defaultCase(node);
    }

    public void caseASequenceItem(ASequenceItem node)
    {
        defaultCase(node);
    }

    public void caseANotSequenceItem(ANotSequenceItem node)
    {
        defaultCase(node);
    }

    public void caseADisjunctionSequenceItem(ADisjunctionSequenceItem node)
    {
        defaultCase(node);
    }

    public void caseAPermutationSequenceItem(APermutationSequenceItem node)
    {
        defaultCase(node);
    }

    public void caseANoparamEventItem(ANoparamEventItem node)
    {
        defaultCase(node);
    }

    public void caseAParamEventItem(AParamEventItem node)
    {
        defaultCase(node);
    }

    public void caseAIdType(AIdType node)
    {
        defaultCase(node);
    }

    public void caseASexType(ASexType node)
    {
        defaultCase(node);
    }

    public void caseABirthyearType(ABirthyearType node)
    {
        defaultCase(node);
    }

    public void caseADiagnosisType(ADiagnosisType node)
    {
        defaultCase(node);
    }

    public void caseAPostalcodeType(APostalcodeType node)
    {
        defaultCase(node);
    }

    public void caseAYearsType(AYearsType node)
    {
        defaultCase(node);
    }

    public void caseAMonthsType(AMonthsType node)
    {
        defaultCase(node);
    }

    public void caseADaysType(ADaysType node)
    {
        defaultCase(node);
    }

    public void caseADateType(ADateType node)
    {
        defaultCase(node);
    }

    public void caseAEventType(AEventType node)
    {
        defaultCase(node);
    }

    public void caseATimeType(ATimeType node)
    {
        defaultCase(node);
    }

    public void caseANumberListItemTypedList(ANumberListItemTypedList node)
    {
        defaultCase(node);
    }

    public void caseAMaleListItemTypedList(AMaleListItemTypedList node)
    {
        defaultCase(node);
    }

    public void caseAFemaleListItemTypedList(AFemaleListItemTypedList node)
    {
        defaultCase(node);
    }

    public void caseAPostalcodeListItemTypedList(APostalcodeListItemTypedList node)
    {
        defaultCase(node);
    }

    public void caseAYearListItemTypedList(AYearListItemTypedList node)
    {
        defaultCase(node);
    }

    public void caseAMonthsListItemTypedList(AMonthsListItemTypedList node)
    {
        defaultCase(node);
    }

    public void caseADaysListItemTypedList(ADaysListItemTypedList node)
    {
        defaultCase(node);
    }

    public void caseADateListItemTypedList(ADateListItemTypedList node)
    {
        defaultCase(node);
    }

    public void caseAStringListItemTypedList(AStringListItemTypedList node)
    {
        defaultCase(node);
    }

    public void caseATimeListItemTypedList(ATimeListItemTypedList node)
    {
        defaultCase(node);
    }

    public void caseAExpandableListItemTypedList(AExpandableListItemTypedList node)
    {
        defaultCase(node);
    }

    public void caseATypedListTail(ATypedListTail node)
    {
        defaultCase(node);
    }

    public void caseATypedName(ATypedName node)
    {
        defaultCase(node);
    }

    public void caseAPatientActor(APatientActor node)
    {
        defaultCase(node);
    }

    public void caseADoctorActor(ADoctorActor node)
    {
        defaultCase(node);
    }

    public void caseADiagnosisActor(ADiagnosisActor node)
    {
        defaultCase(node);
    }

    public void caseTTScript(TTScript node)
    {
        defaultCase(node);
    }

    public void caseTTBy(TTBy node)
    {
        defaultCase(node);
    }

    public void caseTTOf(TTOf node)
    {
        defaultCase(node);
    }

    public void caseTTTo(TTTo node)
    {
        defaultCase(node);
    }

    public void caseTTIn(TTIn node)
    {
        defaultCase(node);
    }

    public void caseTTUse(TTUse node)
    {
        defaultCase(node);
    }

    public void caseTTEnd(TTEnd node)
    {
        defaultCase(node);
    }

    public void caseTTNot(TTNot node)
    {
        defaultCase(node);
    }

    public void caseTTGroup(TTGroup node)
    {
        defaultCase(node);
    }

    public void caseTTPeriod(TTPeriod node)
    {
        defaultCase(node);
    }

    public void caseTTPopulation(TTPopulation node)
    {
        defaultCase(node);
    }

    public void caseTTDoctorFilter(TTDoctorFilter node)
    {
        defaultCase(node);
    }

    public void caseTTEvents(TTEvents node)
    {
        defaultCase(node);
    }

    public void caseTTForeach(TTForeach node)
    {
        defaultCase(node);
    }

    public void caseTTTable(TTTable node)
    {
        defaultCase(node);
    }

    public void caseTTWhere(TTWhere node)
    {
        defaultCase(node);
    }

    public void caseTTPrint(TTPrint node)
    {
        defaultCase(node);
    }

    public void caseTTNative(TTNative node)
    {
        defaultCase(node);
    }

    public void caseTTLike(TTLike node)
    {
        defaultCase(node);
    }

    public void caseTTSequence(TTSequence node)
    {
        defaultCase(node);
    }

    public void caseTTList(TTList node)
    {
        defaultCase(node);
    }

    public void caseTTBarchart(TTBarchart node)
    {
        defaultCase(node);
    }

    public void caseTTSize(TTSize node)
    {
        defaultCase(node);
    }

    public void caseTTMember(TTMember node)
    {
        defaultCase(node);
    }

    public void caseTTCount(TTCount node)
    {
        defaultCase(node);
    }

    public void caseTTTimeline(TTTimeline node)
    {
        defaultCase(node);
    }

    public void caseTTLengthof(TTLengthof node)
    {
        defaultCase(node);
    }

    public void caseTTMale(TTMale node)
    {
        defaultCase(node);
    }

    public void caseTTFemale(TTFemale node)
    {
        defaultCase(node);
    }

    public void caseTTElement(TTElement node)
    {
        defaultCase(node);
    }

    public void caseTTIdType(TTIdType node)
    {
        defaultCase(node);
    }

    public void caseTTSexType(TTSexType node)
    {
        defaultCase(node);
    }

    public void caseTTBirthyearType(TTBirthyearType node)
    {
        defaultCase(node);
    }

    public void caseTTPatientType(TTPatientType node)
    {
        defaultCase(node);
    }

    public void caseTTDoctorType(TTDoctorType node)
    {
        defaultCase(node);
    }

    public void caseTTDiagnosisType(TTDiagnosisType node)
    {
        defaultCase(node);
    }

    public void caseTTPostalcodeType(TTPostalcodeType node)
    {
        defaultCase(node);
    }

    public void caseTTYearsType(TTYearsType node)
    {
        defaultCase(node);
    }

    public void caseTTMonthsType(TTMonthsType node)
    {
        defaultCase(node);
    }

    public void caseTTDaysType(TTDaysType node)
    {
        defaultCase(node);
    }

    public void caseTTDateType(TTDateType node)
    {
        defaultCase(node);
    }

    public void caseTTEventType(TTEventType node)
    {
        defaultCase(node);
    }

    public void caseTTSequenceType(TTSequenceType node)
    {
        defaultCase(node);
    }

    public void caseTTTimeType(TTTimeType node)
    {
        defaultCase(node);
    }

    public void caseTLParen(TLParen node)
    {
        defaultCase(node);
    }

    public void caseTRParen(TRParen node)
    {
        defaultCase(node);
    }

    public void caseTLBrace(TLBrace node)
    {
        defaultCase(node);
    }

    public void caseTRBrace(TRBrace node)
    {
        defaultCase(node);
    }

    public void caseTComma(TComma node)
    {
        defaultCase(node);
    }

    public void caseTColon(TColon node)
    {
        defaultCase(node);
    }

    public void caseTEquals(TEquals node)
    {
        defaultCase(node);
    }

    public void caseTLAngle(TLAngle node)
    {
        defaultCase(node);
    }

    public void caseTRAngle(TRAngle node)
    {
        defaultCase(node);
    }

    public void caseTLSquare(TLSquare node)
    {
        defaultCase(node);
    }

    public void caseTRSquare(TRSquare node)
    {
        defaultCase(node);
    }

    public void caseTRArrow(TRArrow node)
    {
        defaultCase(node);
    }

    public void caseTPipe(TPipe node)
    {
        defaultCase(node);
    }

    public void caseTTGroupFile(TTGroupFile node)
    {
        defaultCase(node);
    }

    public void caseTTPostalcode(TTPostalcode node)
    {
        defaultCase(node);
    }

    public void caseTTDate(TTDate node)
    {
        defaultCase(node);
    }

    public void caseTTTime(TTTime node)
    {
        defaultCase(node);
    }

    public void caseTTDay(TTDay node)
    {
        defaultCase(node);
    }

    public void caseTTYear(TTYear node)
    {
        defaultCase(node);
    }

    public void caseTTMonth(TTMonth node)
    {
        defaultCase(node);
    }

    public void caseTTNumber(TTNumber node)
    {
        defaultCase(node);
    }

    public void caseTTIdentifier(TTIdentifier node)
    {
        defaultCase(node);
    }

    public void caseTTScriptName(TTScriptName node)
    {
        defaultCase(node);
    }

    public void caseTTDocComment(TTDocComment node)
    {
        defaultCase(node);
    }

    public void caseTEmptySpace(TEmptySpace node)
    {
        defaultCase(node);
    }

    public void caseTLineComment(TLineComment node)
    {
        defaultCase(node);
    }

    public void caseEOF(EOF node)
    {
        defaultCase(node);
    }

    public void defaultCase(@SuppressWarnings("unused") Node node)
    {
        // do nothing
    }
}
