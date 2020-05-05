package org.antlr4gwt.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr4gwt.demo.shared.grammar.SimpleGLexer;
import org.antlr4gwt.demo.shared.grammar.SimpleGParser;

public class SimpleDemo implements EntryPoint {

    public void onModuleLoad() {
        String good = "(a b (c d e f) (a 'mystring' 9))";

        // You type your code here
        final TextArea code = new TextArea();
        code.setWidth("500px");
        code.setText(good);

        // Result label (ok or ko)
        final Label result = new Label();
        Button checkButton = new Button("Check", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                SimpleGParser parser = dummyParser(code.getText());
                if (parser.getNumberOfSyntaxErrors() > 0) {
                    result.setText("KO -- There is " + parser.getNumberOfSyntaxErrors() + " syntax errors");
                } else {
                    result.setText("OK -- No syntax error");
                }
            }
        });

        // Should not use vertical panel
        VerticalPanel panel = new VerticalPanel();
        panel.add(code);
        panel.add(checkButton);
        panel.add(result);
        RootPanel.get("main").add(panel);
    }

    SimpleGParser dummyParser(String txt) {
        SimpleGLexer lexer = new SimpleGLexer(CharStreams.fromString(txt));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SimpleGParser parser = new SimpleGParser(tokens);
        // Actually launch the parsing
        parser.root();
        return parser;
    }
}
