/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import java.util.*;
import otc.analysis.*;

@SuppressWarnings("nls")
public final class AOncoprogramProgram extends PProgram
{
    private PHeader _header_;
    private final LinkedList<PGroupDefinitions> _groupDefinitions_ = new LinkedList<PGroupDefinitions>();
    private final LinkedList<PFilterDefinitions> _filterDefinitions_ = new LinkedList<PFilterDefinitions>();
    private PComputationList _computationList_;

    public AOncoprogramProgram()
    {
        // Constructor
    }

    public AOncoprogramProgram(
        @SuppressWarnings("hiding") PHeader _header_,
        @SuppressWarnings("hiding") List<PGroupDefinitions> _groupDefinitions_,
        @SuppressWarnings("hiding") List<PFilterDefinitions> _filterDefinitions_,
        @SuppressWarnings("hiding") PComputationList _computationList_)
    {
        // Constructor
        setHeader(_header_);

        setGroupDefinitions(_groupDefinitions_);

        setFilterDefinitions(_filterDefinitions_);

        setComputationList(_computationList_);

    }

    @Override
    public Object clone()
    {
        return new AOncoprogramProgram(
            cloneNode(this._header_),
            cloneList(this._groupDefinitions_),
            cloneList(this._filterDefinitions_),
            cloneNode(this._computationList_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAOncoprogramProgram(this);
    }

    public PHeader getHeader()
    {
        return this._header_;
    }

    public void setHeader(PHeader node)
    {
        if(this._header_ != null)
        {
            this._header_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._header_ = node;
    }

    public LinkedList<PGroupDefinitions> getGroupDefinitions()
    {
        return this._groupDefinitions_;
    }

    public void setGroupDefinitions(List<PGroupDefinitions> list)
    {
        this._groupDefinitions_.clear();
        this._groupDefinitions_.addAll(list);
        for(PGroupDefinitions e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public LinkedList<PFilterDefinitions> getFilterDefinitions()
    {
        return this._filterDefinitions_;
    }

    public void setFilterDefinitions(List<PFilterDefinitions> list)
    {
        this._filterDefinitions_.clear();
        this._filterDefinitions_.addAll(list);
        for(PFilterDefinitions e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public PComputationList getComputationList()
    {
        return this._computationList_;
    }

    public void setComputationList(PComputationList node)
    {
        if(this._computationList_ != null)
        {
            this._computationList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._computationList_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._header_)
            + toString(this._groupDefinitions_)
            + toString(this._filterDefinitions_)
            + toString(this._computationList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._header_ == child)
        {
            this._header_ = null;
            return;
        }

        if(this._groupDefinitions_.remove(child))
        {
            return;
        }

        if(this._filterDefinitions_.remove(child))
        {
            return;
        }

        if(this._computationList_ == child)
        {
            this._computationList_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._header_ == oldChild)
        {
            setHeader((PHeader) newChild);
            return;
        }

        for(ListIterator<PGroupDefinitions> i = this._groupDefinitions_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PGroupDefinitions) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        for(ListIterator<PFilterDefinitions> i = this._filterDefinitions_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PFilterDefinitions) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._computationList_ == oldChild)
        {
            setComputationList((PComputationList) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}