/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class ATypedName extends PTypedName
{
    private PType _type_;
    private TTIdentifier _tIdentifier_;

    public ATypedName()
    {
        // Constructor
    }

    public ATypedName(
        @SuppressWarnings("hiding") PType _type_,
        @SuppressWarnings("hiding") TTIdentifier _tIdentifier_)
    {
        // Constructor
        setType(_type_);

        setTIdentifier(_tIdentifier_);

    }

    @Override
    public Object clone()
    {
        return new ATypedName(
            cloneNode(this._type_),
            cloneNode(this._tIdentifier_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseATypedName(this);
    }

    public PType getType()
    {
        return this._type_;
    }

    public void setType(PType node)
    {
        if(this._type_ != null)
        {
            this._type_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._type_ = node;
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
            + toString(this._type_)
            + toString(this._tIdentifier_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._type_ == child)
        {
            this._type_ = null;
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
        if(this._type_ == oldChild)
        {
            setType((PType) newChild);
            return;
        }

        if(this._tIdentifier_ == oldChild)
        {
            setTIdentifier((TTIdentifier) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
