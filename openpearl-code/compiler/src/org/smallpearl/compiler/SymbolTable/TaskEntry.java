/*
 * [The "BSD license"]
 *  Copyright (c) 2012-2016 Marcel Schaible
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.smallpearl.compiler.SymbolTable;

import org.smallpearl.compiler.Defaults;
import org.smallpearl.compiler.SmallPearlParser;

public class TaskEntry extends SymbolTableEntry {

    private Boolean m_isMain;
    private Boolean m_isGlobal;
    private SmallPearlParser.PriorityContext m_priority;

    public TaskEntry() {
    }

    public TaskEntry(String name, SmallPearlParser.PriorityContext priority, Boolean isMain, Boolean isGlobal, SmallPearlParser.TaskDeclarationContext ctx, SymbolTable scope) {
        super(name);

        m_priority = priority;
        m_isMain   = isMain;
        m_isGlobal = isGlobal;

        this.m_ctx = ctx;
        this.scope = scope;
    }

    public String toString(int level) {
        String taskPriority;
        if( m_priority != null ) {
            taskPriority = m_priority.expression().getText();
        }
        else
        {
            taskPriority = Integer.toString(Defaults.DEFAULT_TASK_PRIORITY);

        }

        return  indentString(level) + super.toString(level) + "task" +
                " priority(" +taskPriority + ")" +
                (m_isMain ? " main" : "") +
                (m_isGlobal ? " global" : "") +
                scopeString(level);
    }

    protected String scopeString(int m_level) {
        return scope == null ? "\n" : "\n" + scope.toString(m_level);
    }

    public int getSourceLineNo() {
        return m_ctx.getStart().getLine();
    }

    public int getCharPositionInLine() {
        return m_ctx.getStart().getCharPositionInLine();
    }

    public SymbolTable scope;
    private SmallPearlParser.TaskDeclarationContext m_ctx;
}