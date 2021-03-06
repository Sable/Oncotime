/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class AAllListItemTypedList extends PTypedList
{
    private TTStar _tStar_;
    private PTypedList _typedList_;

    public AAllListItemTypedList()
    {
        // Constructor
    }

    public AAllListItemTypedList(
        @SuppressWarnings("hiding") TTStar _tStar_,
        @SuppressWarnings("hiding") PTypedList _typedList_)
    {
        // Constructor
        setTStar(_tStar_);

        setTypedList(_typedList_);

    }

    @Override
    public Object clone()
    {
        return new AAllListItemTypedList(
            cloneNode(this._tStar_),
            cloneNode(this._typedList_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAllListItemTypedList(this);
    }

    public TTStar getTStar()
    {
        return this._tStar_;
    }

    public void setTStar(TTStar node)
    {
        if(this._tStar_ != null)
        {
            this._tStar_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._tStar_ = node;
    }

    public PTypedList getTypedList()
    {
        return this._typedList_;
    }

    public void setTypedList(PTypedList node)
    {
        if(this._typedList_ != null)
        {
            this._typedList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._typedList_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._tStar_)
            + toString(this._typedList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._tStar_ == child)
        {
            this._tStar_ = null;
            return;
        }

        if(this._typedList_ == child)
        {
            this._typedList_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._tStar_ == oldChild)
        {
            setTStar((TTStar) newChild);
            return;
        }

        if(this._typedList_ == oldChild)
        {
            setTypedList((PTypedList) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
