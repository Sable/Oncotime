/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class ANotSequenceItem extends PSequenceItem
{
    private PEventItem _eventItem_;

    public ANotSequenceItem()
    {
        // Constructor
    }

    public ANotSequenceItem(
        @SuppressWarnings("hiding") PEventItem _eventItem_)
    {
        // Constructor
        setEventItem(_eventItem_);

    }

    @Override
    public Object clone()
    {
        return new ANotSequenceItem(
            cloneNode(this._eventItem_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANotSequenceItem(this);
    }

    public PEventItem getEventItem()
    {
        return this._eventItem_;
    }

    public void setEventItem(PEventItem node)
    {
        if(this._eventItem_ != null)
        {
            this._eventItem_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._eventItem_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._eventItem_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._eventItem_ == child)
        {
            this._eventItem_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._eventItem_ == oldChild)
        {
            setEventItem((PEventItem) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
