/* This file was generated by SableCC (http://www.sablecc.org/). */

package otc.node;

import java.util.*;
import otc.analysis.*;

@SuppressWarnings("nls")
public final class AHeader extends PHeader
{
    private TTScriptName _name_;
    private final LinkedList<PTypedName> _parameters_ = new LinkedList<PTypedName>();
    private TTDocComment _scriptComment_;
    private final LinkedList<PDependencies> _uses_ = new LinkedList<PDependencies>();

    public AHeader()
    {
        // Constructor
    }

    public AHeader(
        @SuppressWarnings("hiding") TTScriptName _name_,
        @SuppressWarnings("hiding") List<PTypedName> _parameters_,
        @SuppressWarnings("hiding") TTDocComment _scriptComment_,
        @SuppressWarnings("hiding") List<PDependencies> _uses_)
    {
        // Constructor
        setName(_name_);

        setParameters(_parameters_);

        setScriptComment(_scriptComment_);

        setUses(_uses_);

    }

    @Override
    public Object clone()
    {
        return new AHeader(
            cloneNode(this._name_),
            cloneList(this._parameters_),
            cloneNode(this._scriptComment_),
            cloneList(this._uses_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAHeader(this);
    }

    public TTScriptName getName()
    {
        return this._name_;
    }

    public void setName(TTScriptName node)
    {
        if(this._name_ != null)
        {
            this._name_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._name_ = node;
    }

    public LinkedList<PTypedName> getParameters()
    {
        return this._parameters_;
    }

    public void setParameters(List<PTypedName> list)
    {
        this._parameters_.clear();
        this._parameters_.addAll(list);
        for(PTypedName e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public TTDocComment getScriptComment()
    {
        return this._scriptComment_;
    }

    public void setScriptComment(TTDocComment node)
    {
        if(this._scriptComment_ != null)
        {
            this._scriptComment_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._scriptComment_ = node;
    }

    public LinkedList<PDependencies> getUses()
    {
        return this._uses_;
    }

    public void setUses(List<PDependencies> list)
    {
        this._uses_.clear();
        this._uses_.addAll(list);
        for(PDependencies e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._name_)
            + toString(this._parameters_)
            + toString(this._scriptComment_)
            + toString(this._uses_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._name_ == child)
        {
            this._name_ = null;
            return;
        }

        if(this._parameters_.remove(child))
        {
            return;
        }

        if(this._scriptComment_ == child)
        {
            this._scriptComment_ = null;
            return;
        }

        if(this._uses_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._name_ == oldChild)
        {
            setName((TTScriptName) newChild);
            return;
        }

        for(ListIterator<PTypedName> i = this._parameters_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PTypedName) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._scriptComment_ == oldChild)
        {
            setScriptComment((TTDocComment) newChild);
            return;
        }

        for(ListIterator<PDependencies> i = this._uses_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PDependencies) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        throw new RuntimeException("Not a child.");
    }
}
