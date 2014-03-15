/*
 *  Copyright (c) 2000-2002, Shayne R Flint
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *
 *  3. The name of the author may not be used to endorse or promote products
 *  derived from this software without specific prior written permission.
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
 
package armidale.utilities.makeclass;

import armidale.api.util.Strings;
import java.io.PrintWriter;
import org.w3c.dom.Node;

class Parameter extends TypedName {

  boolean  setLocal  = false;


  public Parameter(Node node, ArmidaleClass theClass) {
    super(node, theClass);
    Node  tempNode  = node.getAttributes().getNamedItem("setLocal");
    if (tempNode != null) {
      setLocal = tempNode.getNodeValue().toLowerCase().equals("yes");
    }
  }


  public Parameter(String typeName, String name, String constants, boolean setLocal, ArmidaleClass theClass) {
    super(typeName, name, constants, theClass);
    this.setLocal = setLocal;
  }


  public String formalParameter() {
    return typeDeclaration() + " " + parameterName();
  }


  public String parameterName() {
    return Strings.lowercase(name);
  }


  public void writeDocumentation(PrintWriter output) {
    writeDocumentation(output, "@param " + name);
    if (constants != null && !constants.qualifiedName.equals("")) {
      output.println("   * Values for this parameter are defined in the {@link " + constants.qualifiedName + " " + constants.qualifiedName + "} interface");
    }
  }

}

