# Instruction Roadmap

⚠️ ADC (ADd with Carry)
--------------------

Affects Flags: N V Z C - Flag Checks ❌ 

    MODE           SYNTAX       HEX LEN TIM [Standard] [ BCD ]
    Immediate     ADC #$44      $69  2   2      ✅        ❌
    Zero Page     ADC $44       $65  2   3      ✅        ❌
    Zero Page,X   ADC $44,X     $75  2   4      ✅        ❌
    Absolute      ADC $4400     $6D  3   4      ✅        ❌
    Absolute,X    ADC $4400,X   $7D  3   4+     ✅        ❌
    Absolute,Y    ADC $4400,Y   $79  3   4+     ✅        ❌
    Indirect,X    ADC ($44,X)   $61  2   6      ✅        ❌
    Indirect,Y    ADC ($44),Y   $71  2   5+     ✅        ❌

    + add 1 cycle if page boundary crossed


✅ AND (bitwise AND with accumulator)
----------------------------------

Affects Flags: N Z - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Immediate     AND #$44      $29  2   2      ✅ 
    Zero Page     AND $44       $25  2   3      ✅
    Zero Page,X   AND $44,X     $35  2   4      ✅
    Absolute      AND $4400     $2D  3   4      ✅
    Absolute,X    AND $4400,X   $3D  3   4+     ✅
    Absolute,Y    AND $4400,Y   $39  3   4+     ✅
    Indirect,X    AND ($44,X)   $21  2   6      ✅
    Indirect,Y    AND ($44),Y   $31  2   5+     ✅

    + add 1 cycle if page boundary crossed


✅ ASL (Arithmetic Shift Left)
---------------------------

Affects Flags: N Z C - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Accumulator   ASL A         $0A  1   2      ✅
    Zero Page     ASL $44       $06  2   5      ✅
    Zero Page,X   ASL $44,X     $16  2   6      ✅
    Absolute      ASL $4400     $0E  3   6      ✅
    Absolute,X    ASL $4400,X   $1E  3   7      ✅


✅ BIT (test BITs)
---------------

Affects Flags: N V Z - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Zero Page     BIT $44       $24  2   3      ✅
    Absolute      BIT $4400     $2C  3   4      ✅


✅ Branch Instructions
-------------------

Affect Flags: none

    MNEMONIC                       HEX       [Status]
    BPL (Branch on PLus)           $10          ✅
    BMI (Branch on MInus)          $30          ✅
    BVC (Branch on oVerflow Clear) $50          ✅
    BVS (Branch on oVerflow Set)   $70          ✅
    BCC (Branch on Carry Clear)    $90          ✅
    BCS (Branch on Carry Set)      $B0          ✅
    BNE (Branch on Not Equal)      $D0          ✅
    BEQ (Branch on EQual)          $F0          ✅

✅ BRK (BReaK)
-----------

Affects Flags: B - Flag Checks ✅

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Implied       BRK           $00  1   7      ✅

✅ CMP (CoMPare accumulator)
-------------------------

Affects Flags: N Z C - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Immediate     CMP #$44      $C9  2   2      ✅
    Zero Page     CMP $44       $C5  2   3      ✅
    Zero Page,X   CMP $44,X     $D5  2   4      ✅
    Absolute      CMP $4400     $CD  3   4      ✅
    Absolute,X    CMP $4400,X   $DD  3   4+     ✅
    Absolute,Y    CMP $4400,Y   $D9  3   4+     ✅
    Indirect,X    CMP ($44,X)   $C1  2   6      ✅
    Indirect,Y    CMP ($44),Y   $D1  2   5+     ✅

    + add 1 cycle if page boundary crossed

✅ CPX (ComPare X register)
------------------------

Affects Flags: N Z C - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Immediate     CPX #$44      $E0  2   2      ✅
    Zero Page     CPX $44       $E4  2   3      ✅
    Absolute      CPX $4400     $EC  3   4      ✅

✅ CPY (ComPare Y register)
------------------------

Affects Flags: N Z C - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Immediate     CPY #$44      $C0  2   2      ✅
    Zero Page     CPY $44       $C4  2   3      ✅
    Absolute      CPY $4400     $CC  3   4      ✅

✅ DEC (DECrement memory)
----------------------

Affects Flags: N Z - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Zero Page     DEC $44       $C6  2   5      ✅
    Zero Page,X   DEC $44,X     $D6  2   6      ✅
    Absolute      DEC $4400     $CE  3   6      ✅
    Absolute,X    DEC $4400,X   $DE  3   7      ✅

✅ EOR (bitwise Exclusive OR)
--------------------------

Affects Flags: N Z - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Immediate     EOR #$44      $49  2   2      ✅
    Zero Page     EOR $44       $45  2   3      ✅
    Zero Page,X   EOR $44,X     $55  2   4      ✅
    Absolute      EOR $4400     $4D  3   4      ✅
    Absolute,X    EOR $4400,X   $5D  3   4+     ✅
    Absolute,Y    EOR $4400,Y   $59  3   4+     ✅
    Indirect,X    EOR ($44,X)   $41  2   6      ✅
    Indirect,Y    EOR ($44),Y   $51  2   5+     ✅

    + add 1 cycle if page boundary crossed

✅ Flag (Processor Status) Instructions
------------------------------------

Affect Flags: as noted - Flag Checks ✅

    MNEMONIC                       HEX       [Status]
    CLC (CLear Carry)              $18          ✅
    SEC (SEt Carry)                $38          ✅
    CLI (CLear Interrupt)          $58          ✅
    SEI (SEt Interrupt)            $78          ✅
    CLV (CLear oVerflow)           $B8          ✅
    CLD (CLear Decimal)            $D8          ✅
    SED (SEt Decimal)              $F8          ✅



✅ INC (INCrement memory)
----------------------

Affects Flags: N Z - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Zero Page     INC $44       $E6  2   5      ✅
    Zero Page,X   INC $44,X     $F6  2   6      ✅
    Absolute      INC $4400     $EE  3   6      ✅
    Absolute,X    INC $4400,X   $FE  3   7      ✅

✅ JMP (JuMP)
----------

Affects Flags: none

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Absolute      JMP $5597     $4C  3   3      ✅
    Indirect      JMP ($5597)   $6C  3   5      ✅

❌ JSR (Jump to SubRoutine)
------------------------

Affects Flags: none

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Absolute      JSR $5597     $20  3   6      ❌

✅ LDA (LoaD Accumulator)
----------------------

Affects Flags: N Z - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Immediate     LDA #$44      $A9  2   2      ✅
    Zero Page     LDA $44       $A5  2   3      ✅
    Zero Page,X   LDA $44,X     $B5  2   4      ✅
    Absolute      LDA $4400     $AD  3   4      ✅
    Absolute,X    LDA $4400,X   $BD  3   4+     ✅
    Absolute,Y    LDA $4400,Y   $B9  3   4+     ✅
    Indirect,X    LDA ($44,X)   $A1  2   6      ✅
    Indirect,Y    LDA ($44),Y   $B1  2   5+     ✅

    + add 1 cycle if page boundary crossed

✅ LDX (LoaD X register)
---------------------

Affects Flags: N Z - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Immediate     LDX #$44      $A2  2   2      ✅
    Zero Page     LDX $44       $A6  2   3      ✅
    Zero Page,Y   LDX $44,Y     $B6  2   4      ✅
    Absolute      LDX $4400     $AE  3   4      ✅
    Absolute,Y    LDX $4400,Y   $BE  3   4+     ✅

    + add 1 cycle if page boundary crossed

✅ LDY (LoaD Y register)
---------------------

Affects Flags: N Z - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Immediate     LDY #$44      $A0  2   2      ✅
    Zero Page     LDY $44       $A4  2   3      ✅
    Zero Page,X   LDY $44,X     $B4  2   4      ✅
    Absolute      LDY $4400     $AC  3   4      ✅
    Absolute,X    LDY $4400,X   $BC  3   4+     ✅

    + add 1 cycle if page boundary crossed

❌ LSR (Logical Shift Right)
-------------------------

Affects Flags: N Z C - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Accumulator   LSR A         $4A  1   2      ❌
    Zero Page     LSR $44       $46  2   5      ❌
    Zero Page,X   LSR $44,X     $56  2   6      ❌
    Absolute      LSR $4400     $4E  3   6      ❌
    Absolute,X    LSR $4400,X   $5E  3   7      ❌


✅ NOP (No OPeration)
------------------

Affects Flags: none

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Implied       NOP           $EA  1   2      ✅

✅ ORA (bitwise OR with Accumulator)
---------------------------------

Affects Flags: N Z - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Immediate     ORA #$44      $09  2   2      ✅
    Zero Page     ORA $44       $05  2   3      ✅
    Zero Page,X   ORA $44,X     $15  2   4      ✅
    Absolute      ORA $4400     $0D  3   4      ✅
    Absolute,X    ORA $4400,X   $1D  3   4+     ✅
    Absolute,Y    ORA $4400,Y   $19  3   4+     ✅
    Indirect,X    ORA ($44,X)   $01  2   6      ✅
    Indirect,Y    ORA ($44),Y   $11  2   5+     ✅

    + add 1 cycle if page boundary crossed

✅ Register Instructions
---------------------

Affect Flags: N Z - Flag Checks ❌

    MNEMONIC                 HEX             [Status]
    TAX (Transfer A to X)    $AA                ✅
    TXA (Transfer X to A)    $8A                ✅
    DEX (DEcrement X)        $CA                ✅
    INX (INcrement X)        $E8                ✅
    TAY (Transfer A to Y)    $A8                ✅
    TYA (Transfer Y to A)    $98                ✅
    DEY (DEcrement Y)        $88                ✅
    INY (INcrement Y)        $C8                ✅

❌ ROL (ROtate Left)
-----------------

Affects Flags: N Z C - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Accumulator   ROL A         $2A  1   2      ❌
    Zero Page     ROL $44       $26  2   5      ❌
    Zero Page,X   ROL $44,X     $36  2   6      ❌
    Absolute      ROL $4400     $2E  3   6      ❌
    Absolute,X    ROL $4400,X   $3E  3   7      ❌

❌ ROR (ROtate Right)
------------------

Affects Flags: N Z C - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Accumulator   ROR A         $6A  1   2      ❌
    Zero Page     ROR $44       $66  2   5      ❌
    Zero Page,X   ROR $44,X     $76  2   6      ❌
    Absolute      ROR $4400     $6E  3   6      ❌
    Absolute,X    ROR $4400,X   $7E  3   7      ❌

❌ RTI (ReTurn from Interrupt)
---------------------------

Affects Flags: all - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Implied       RTI           $40  1   6      ❌

❌ RTS (ReTurn from Subroutine)
----------------------------

Affects Flags: none

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Implied       RTS           $60  1   6      ❌

✅ SBC (SuBtract with Carry)
-------------------------

Affects Flags: N V Z C - Flag Checks ❌

    MODE           SYNTAX       HEX LEN TIM  [Status] [ BCD ]
    Immediate     SBC #$44      $E9  2   2      ✅       ❌
    Zero Page     SBC $44       $E5  2   3      ✅       ❌
    Zero Page,X   SBC $44,X     $F5  2   4      ✅       ❌
    Absolute      SBC $4400     $ED  3   4      ✅       ❌
    Absolute,X    SBC $4400,X   $FD  3   4+     ✅       ❌
    Absolute,Y    SBC $4400,Y   $F9  3   4+     ✅       ❌
    Indirect,X    SBC ($44,X)   $E1  2   6      ✅       ❌
    Indirect,Y    SBC ($44),Y   $F1  2   5+     ✅       ❌

    + add 1 cycle if page boundary crossed

✅ STA (STore Accumulator)
-----------------------

Affects Flags: none

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Zero Page     STA $44       $85  2   3      ✅
    Zero Page,X   STA $44,X     $95  2   4      ✅
    Absolute      STA $4400     $8D  3   4      ✅
    Absolute,X    STA $4400,X   $9D  3   5      ✅
    Absolute,Y    STA $4400,Y   $99  3   5      ✅
    Indirect,X    STA ($44,X)   $81  2   6      ✅
    Indirect,Y    STA ($44),Y   $91  2   6      ✅

❌ Stack Instructions
------------------

    MNEMONIC                        HEX TIM  [Status]
    TXS (Transfer X to Stack ptr)   $9A  2      ❌
    TSX (Transfer Stack ptr to X)   $BA  2      ❌
    PHA (PusH Accumulator)          $48  3      ❌
    PLA (PuLl Accumulator)          $68  4      ❌
    PHP (PusH Processor status)     $08  3      ❌
    PLP (PuLl Processor status)     $28  4      ❌

✅ STX (STore X register)
----------------------

Affects Flags: none

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Zero Page     STX $44       $86  2   3      ✅
    Zero Page,Y   STX $44,Y     $96  2   4      ✅
    Absolute      STX $4400     $8E  3   4      ✅

✅ STY (STore Y register)
----------------------

Affects Flags: none

    MODE           SYNTAX       HEX LEN TIM  [Status]
    Zero Page     STY $44       $84  2   3      ✅
    Zero Page,X   STY $44,X     $94  2   4      ✅
    Absolute      STY $4400     $8C  3   4      ✅
