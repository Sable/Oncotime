/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import java.util.*;
import otc.analysis.*;

@SuppressWarnings("nls")
public final class APrintAttrComputation extends PComputation
{
    private final LinkedList<PType> _attrList_ = new LinkedList<PType>();
    private TTIdentifier _tIdentifier_;

    public APrintAttrComputation()
    {
        // Constructor
    }

    public APrintAttrComputation(
        @SuppressWarnings("hiding") List<PType> _attrList_,
        @SuppressWarnings("hiding") TTIdentifier _tIdentifier_)
    {
        // Constructor
        setAttrList(_attrList_);

        setTIdentifier(_tIdentifier_);

    }

    @Override
    public Object clone()
    {
        return new APrintAttrComputation(
            cloneList(this._attrList_),
            cloneNode(this._tIdentifier_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPrintAttrComputation(this);
    }

    public LinkedList<PType> getAttrList()
    {
        return this._attrList_;
    }

    public void setAttrList(List<PType> list)
    {
        this._attrList_.clear();
        this._attrList_.addAll(list);
        for(PType e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public TTIdentifier getTIdentifier()
    {
        return this._tIdentifier_;
    }

    public void setTIdentifier(TTIdentifier node)
    {
        if(this._tIdentifier_ != null)
        {
            this._tIdentifier_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._tIdentifier_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._attrList_)
            + toString(this._tIdentifier_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._attrList_.remove(child))
        {
            return;
        }

        if(this._tIdentifier_ == child)
        {
            this._tIdentifier_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        for(ListIterator<PType> i = this._attrList_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PType) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._tIdentifier_ == oldChild)
        {
            setTIdentifier((TTIdentifier) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
