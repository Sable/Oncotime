/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import otc.analysis.*;

@SuppressWarnings("nls")
public final class AForeachTableSetComputation extends PComputation
{
    private TTIdentifier _iterator_;
    private TTIdentifier _variable_;
    private PComputationList _computationList_;

    public AForeachTableSetComputation()
    {
        // Constructor
    }

    public AForeachTableSetComputation(
        @SuppressWarnings("hiding") TTIdentifier _iterator_,
        @SuppressWarnings("hiding") TTIdentifier _variable_,
        @SuppressWarnings("hiding") PComputationList _computationList_)
    {
        // Constructor
        setIterator(_iterator_);

        setVariable(_variable_);

        setComputationList(_computationList_);

    }

    @Override
    public Object clone()
    {
        return new AForeachTableSetComputation(
            cloneNode(this._iterator_),
            cloneNode(this._variable_),
            cloneNode(this._computationList_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAForeachTableSetComputation(this);
    }

    public TTIdentifier getIterator()
    {
        return this._iterator_;
    }

    public void setIterator(TTIdentifier node)
    {
        if(this._iterator_ != null)
        {
            this._iterator_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._iterator_ = node;
    }

    public TTIdentifier getVariable()
    {
        return this._variable_;
    }

    public void setVariable(TTIdentifier node)
    {
        if(this._variable_ != null)
        {
            this._variable_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._variable_ = node;
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
            + toString(this._iterator_)
            + toString(this._variable_)
            + toString(this._computationList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._iterator_ == child)
        {
            this._iterator_ = null;
            return;
        }

        if(this._variable_ == child)
        {
            this._variable_ = null;
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
        if(this._iterator_ == oldChild)
        {
            setIterator((TTIdentifier) newChild);
            return;
        }

        if(this._variable_ == oldChild)
        {
            setVariable((TTIdentifier) newChild);
            return;
        }

        if(this._computationList_ == oldChild)
        {
            setComputationList((PComputationList) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
